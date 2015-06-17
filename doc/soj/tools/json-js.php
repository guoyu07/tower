<?php

$handle = fopen("php://stdin", "r");

if ($argc == 1) {
    exit();
}

while (!feof($handle)) {
    $str = trim(fgets($handle));
    if (empty($str)) continue;

    $arr = json_decode($str, true);

    $out = array();
    for ($i=1;$i<$argc;$i++) {
        $v = $arr[$argv[$i]];
        $out[] = $v;
    }

    echo implode("\t", $out), "\n";
}
