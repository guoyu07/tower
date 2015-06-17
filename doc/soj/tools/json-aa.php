<?php

error_reporting(E_ERROR);

$ptn = "/tracker\.site\.(.+?):\{(.+)\}/";
$handle = fopen("php://stdin", "r");

if ($argc == 1) {
    exit();
}

while (!feof($handle)) {
    $str = trim(fgets($handle));
    if (empty($str)) continue;

    preg_match($ptn, $str, $matches);

    if (!empty($matches)) {
        $json = "{" . $matches[2] . "}";
        $arr = json_decode($json, true);
        $arr["key"] = $matches[1];
    } else {
        $arr = json_decode($str, true);
    }

    $arr["ctime"] = date("Y-m-d H:i:s", substr($arr["cstamp"], 0, -3));
    $out = array();
    for ($i=1;$i<$argc;$i++) {
        $v = $arr[$argv[$i]];
	if(empty($v)){
		$v = '{placeholder}';
	}
        $out[] = $v;
    }

    echo implode("\t", $out), "\n";
}
