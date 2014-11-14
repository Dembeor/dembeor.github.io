<?php
$plik="licz.txt";
$file=fopen($plik, "r");
$liczba=fgets($file, 16);
fclose($file);
$liczba++;
$file=fopen($plik, "w");
fwrite($file, $liczba++);
fclose($file);
}
?>
