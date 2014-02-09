<?php

	$user = 'root';
	$password = 'toor';
	$conn_str='mysql:host=localhost;dbname=assassin';
	mysql_connect("localhost","root","toor");
	mysql_select_db("assassin");

function train($userid,$files) {
	// /boilermake/Compare userid (-train|-update|-predict) num_files file1 file2 ... filen
	$num_files = count($files);
	$cmd = "cd compare && .Compare -train $userid $num_files";
	$cmd2 = "chmod 777";
	foreach($files as $file) {
		$cmd = $cmd . " $file";
		$cmd2 = $cmd2 . " $file";
	}	
	echo $cmd;
	echo "<br>$cmd2";
	exec($cmd2);
	exec($cmd . " >> compare/logs/$userid.log &>> compare/logs/&userid.log");
	
}

$photos = "/var/www/Assassin/server/photos/";
?>