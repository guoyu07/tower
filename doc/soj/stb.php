<?php
// error_reporting(E_ALL^E_NOTICE);
// header('HTTP/1.0 204 No Content');
// header('Content-Length: 0');
header('P3P: CP="CURa ADMa DEVa PSAo PSDo OUR BUS UNI PUR INT DEM STA PRE COM NAV OTC NOI DSP COR"');
// header("Content-Type: application/x-javascript");
header("Content-Type: text/plain");
header("Cache-Control: no-cache");

define('COOKIE_NAME',      '_stat_guid');
define('COOKIE_RFPN_NAME', '_stat_rfpn');

define('PREV_GUID_COOKIE_NAME', '_prev_stat_guid');

define('STAMP_KEY',   'stamp');
define('NAME_KEY',    'name');
define('CONTENT_KEY', 'content');
define('SITE_KEY',    'site');

define('UGUID_KEY',          'uguid');
define('GUID_KEY',           'guid');
define('USER_ID_KEY',        'uid');
define('CITY_ID_KEY',        'cid');
define('LAST_USER_INFO_KEY', 'lui');

define('PAGE_NAME',        'pn');
define('CLIENT_IP_KEY',    'cip');
define('URL_KEY',          'url');
define('METHOD_KEY',       'method');
define('REFERER_KEY',      'referer');
define('RF_PN_KEY',        'rfpn');
define('USER_AGENT_KEY',   'agent');
define('SESSION_ID_KEY',   'sessid');
define('CLIENT_STAMP_KEY', 'cstamp');
define('LOAD_TIME_KEY',    'loadtime');
define('SCREEN_KEY',       'sc');
define('CUSTOM_PARAM',     'cstparam');

define('PREV_GUID_KEY',    'prev_guid');
define('COOKIES_KEY',    'cookies');

define('GENERAL_SEQ', '_general_seq');
define('UNIQID_KEY', 'uniqid');

define('TIME_KEY', 'time');
define('CLIENT_TIME_KEY', 'ctime');

include('Guid.php');
include('func.php');

$parameters = array_merge($_GET, $_POST);

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
$pn = $parameters["p"];
$name = "tracker.site.".$pn;

$date = date("Y-m-d_H");
$log_file = "/data1/aa_log/$date.log";
$client_ip = get_client_ip();

$blocker_enable = is_blocker_enable();
$blocked = FALSE;

if ($blocker_enable) {
    $invalid_ips = get_invalid_ips();

    foreach ($invalid_ips as $pattern) {
        if (fnmatch($pattern, $client_ip)) {
            $log_file = "/data1/aa_log/blocked.$date.log";
            $blocked = TRUE;
            break;
        }
    }
}


$log[PAGE_NAME] = (string)$parameters["pn"];
$log[GUID_KEY] = (string)$parameters["guid"];
$log[UGUID_KEY] = $sguid;
$log[URL_KEY] = (string)$parameters["h"];
$log[REFERER_KEY] = (string)$parameters["r"];
$log[SITE_KEY] = (string)$parameters["site"];
$log[STAMP_KEY] = (string)$sec . sprintf('%03d', (int)($usec * 1000));
$log[CLIENT_IP_KEY] = (string)get_client_ip();
$log[SESSION_ID_KEY] = (string)$parameters["ssid"];
$log[LOAD_TIME_KEY] = (string)$parameters["lt"];

$log[USER_ID_KEY] = (string)$parameters["uid"];
$log[CITY_ID_KEY] = (string)$parameters["ctid"];
$log[LAST_USER_INFO_KEY] = (string)$parameters["luid"];
$log[CLIENT_STAMP_KEY] = (string)$parameters["t"];
$log[CUSTOM_PARAM] = (string)$parameters["cp"];

$screen = json_decode($parameters['sc'], TRUE);

if ($screen) {
    $log[SCREEN_KEY] = $screen;
}

$log[TIME_KEY] = (string)date("Y-m-d H:i:s", (int)$log[STAMP_KEY] / 1000);
if ($log[CLIENT_STAMP_KEY]) {
    $log[CLIENT_TIME_KEY] = (string)date("Y-m-d H:i:s",
                                         (int)$log[CLIENT_STAMP_KEY] / 1000);
}

if ( ! $blocked ) {
    // increase seq
    $mem = get_memcached();
    $log[GENERAL_SEQ] = inc_general_seq($mem);

}

if (isset($_COOKIE[COOKIE_RFPN_NAME])) {
    $log[RF_PN_KEY] = $_COOKIE[COOKIE_RFPN_NAME];
}
setcookie(COOKIE_RFPN_NAME, $pn);

// record the previous guid
if (isset($_COOKIE[PREV_GUID_COOKIE_NAME]) &&
    !empty($_COOKIE[PREV_GUID_COOKIE_NAME])) {
    $prev_guid = $_COOKIE[PREV_GUID_COOKIE_NAME];
    if ($_COOKIE[PREV_GUID_COOKIE_NAME] != (string)$parameters['guid']) {
        setcookie(PREV_GUID_COOKIE_NAME, (string)$parameters['guid']);
    }
}
else{
    $prev_guid = (string)$parameters["guid"];
    setcookie(PREV_GUID_COOKIE_NAME, $prev_guid);
}

$log[PREV_GUID_KEY] = $prev_guid;

$log[METHOD_KEY] = (string)$parameters["m"];
$log[USER_AGENT_KEY] = $_SERVER["HTTP_USER_AGENT"];

$tmp_guid = new Util_Guid();
$log[UNIQID_KEY] = $tmp_guid->valueAfterMD5;

if (!$log[GUID_KEY]) {
    $log_file = "/data1/aa_log/invalid.$date.log";
}

// filter
foreach ($log as $k => $v) {
    if ($v === "") unset($log[$k]);
}

// write
$json = @json_encode($log);
write_log("$name:$json\n", $log_file);

function write_log($log, $log_file) {
    file_put_contents($log_file, $log, FILE_APPEND | LOCK_EX );
}
