<?php
header('P3P: CP="CURa ADMa DEVa PSAo PSDo OUR BUS UNI PUR INT DEM STA PRE COM NAV OTC NOI DSP COR"');
header("Content-Type: application/x-javascript");
header("Cache-Control: no-cache");

define('COOKIE_NAME',      '_stat_guid');

define('STAMP_KEY',        'stamp');
define('UGUID_KEY',        'uguid');
define('CLIENT_IP_KEY',    'cip');
define('REFERER_KEY',      'referer');
define('USER_AGENT_KEY',   'agent');
define('CLIENT_STAMP_KEY', 'cstamp');

define('EXECPTION_REASON_KEY', 'reason');
define('URL_KEY',              'url');
define('MSG_KEY',              'msg');
define('LINE_NUMBER_KEY',      'line');

include('Guid.php');
include('func.php');

// cookie
if (isset($_COOKIE[COOKIE_NAME]) && !empty($_COOKIE[COOKIE_NAME])) {
    $sguid = $_COOKIE[COOKIE_NAME];
} else {
    $CGuid = new Util_Guid();
    $sguid = $CGuid->toString();
    setcookie(COOKIE_NAME, $sguid, 4102415999);
}

// build
list($usec, $sec) = explode(' ', microtime());

$log[STAMP_KEY]        = (string)$sec . sprintf('%03d', (int)($usec * 1000));
$log[UGUID_KEY]        = $sguid;
$log[CLIENT_IP_KEY]    = (string)get_client_ip();
$log[CLIENT_STAMP_KEY] = (string)@$_GET['t'];
$log[REFERER_KEY]      = (string)@$_GET['rf'];
$log[USER_AGENT_KEY]   = (string)@$_SERVER['HTTP_USER_AGENT'];

$log[EXECPTION_REASON_KEY] = (string)@$_GET['r'];
$log[URL_KEY]              = (string)@$_GET['u'];
$log[MSG_KEY]              = (string)@$_GET['m'];
$log[LINE_NUMBER_KEY]      = (string)@$_GET['l'];

// filter
foreach ($log as $k => $v) {
    if ($v === "") unset($log[$k]);
}

// write
$json = json_encode($log);
write_log("$json\n");

function write_log($log) {
    $d = date("Y-m-d");
    $f = fopen("/data1/jsec_log/$d.log", 'a');
    fwrite($f, $log);
    fclose($f);
}
