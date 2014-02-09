<?php
include('lib.php');
//error_reporting(E_ALL);
// Check to make sure the "id" is actually something that's in the database.
$id = $_REQUEST['id'];
$sql = "SELECT id FROM photos WHERE id='$id'";

//$num = $db->num_rows($db->query($sql));
//if($num <= 0)
//	exit("1"); //ID wasn't in the database
$path = $photos;
if(isset($_REQUEST['type']))
	$path = $_REQUEST['type'] == "1" ? $thumbs : $photos;
header('Content-Type: image/jpg');
$file = $path.$_REQUEST['id'].".bmp";
$img = new Imagick($file);
$img->setCompression(Imagick::COMPRESSION_JPEG);
$img->setImageFormat('jpg');
echo $img;
$img->destroy();
?>