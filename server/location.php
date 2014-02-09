<?php
include('lib.php');
$userid = $_POST['userid'];
$row = mysql_fetch_array(mysql_query("SELECT * FROM targets WHERE hunter_id = '$userid'"));
$target = $row['userid'];
$user = mysql_fetch_array(mysql_query("SELECT latitude,longitude FROM users WHERE id='$target'"));
print json_encode($user);
?>