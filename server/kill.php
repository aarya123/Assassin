<?
$uploaddir = $photos;
if (!isset($_FILES)) {
	print json_encode(array('error' => 'No files recieved'));
} else {
    $file = $_FILES[0];
	$extension = end(explode(".", $file["name"]));
	$path = $file['tmp_name'];
	$image = $file['name'];
	$file = basename($file['name']);
	$ext = $extension;
	$id=0;
	$dbh = new PDO($conn_str, $user, $password);
	//echo "id: $userid<br>";
	$stmt = $dbh->prepare('INSERT INTO photos (userid) VALUES ('. $userid . ')');
	$stmt->bindParam(':userid', $_POST['userid']);
	if($stmt->execute()) {
		$id = $dbh->lastInsertId();
	} else {
		print json_encode(array('error' => $stmt->errorInfo()));
		exit();
	}
	
	$uploadpath = $uploaddir.$id . '.bmp';
	move_uploaded_file($path,$uploadpath);
	$img = new Imagick($uploadpath);
	$ort = $img->getImageProperty('Exif:Orientation');
	$img->stripImage();
	switch($ort)
	{
		case 3: // 180 rotate left
		$img->rotateImage(new ImagickPixel('none'),-180);
		break;
		
		
		case 6: // 90 rotate right
		$img->rotateImage(new ImagickPixel('none'),90);
		break;
		
		case 8:    // 90 rotate left
		$img->rotateImage(new ImagickPixel('none'),-90);
		break;
		
	}
	
	//$img->rotateImage(new ImagickPixel('none'),90);
	$img->scaleImage(640,853);
	$img->setImageColorspace(255); 
	$img->setCompression(Imagick::COMPRESSION_NO);
	$img->setImageFormat('bmp');
	$img->writeImage($uploadpath);
	$img->destroy();
}
?>