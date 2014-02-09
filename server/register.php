<?php
//include_once('db.php');
include_once('lib.php');
if(!array_key_exists('password', $_POST) || !array_key_exists('email_address', $_POST) ||
   !array_key_exists('sex', $_POST) || !array_key_exists('age', $_POST) || !array_key_exists('race', $_POST) ||
   !array_key_exists('height', $_POST) || !array_key_exists('locations', $_POST)) {
	print json_encode(array('error' => 'usage: post password, email_address, name, sex, age, race, height, locations (comma separated)'));
	exit();
}
function assert_value_unique($dbh, $field, $value) {
	$stmt = $dbh->prepare("SELECT count({$field}) FROM users WHERE {$field} = :value");
	$stmt->bindParam(':value', $value);
	$stmt->execute();
	$value_exists = $stmt->fetchColumn() > 0;
	if($value_exists) {
		print json_encode(array('error' => $field . ' already exists'));
		exit();
	}
}
try {
	$dbh = new PDO($conn_str, $user, $password);
	assert_value_unique($dbh, 'email_address', $_POST['email_address']);
	$stmt = $dbh->prepare('INSERT INTO users (password_hash, email_address, sex, age, race, height) VALUES (:password_hash, :email_address, :sex, :age, :race, :height)');
	$stmt->bindParam(':password_hash', md5($_POST['password']));
	$stmt->bindParam(':email_address', $_POST['email_address']);
	$stmt->bindParam(':sex', $_POST['sex']);
	$stmt->bindParam(':age', $_POST['age']);
	$stmt->bindParam(':race', $_POST['race']);
	$stmt->bindParam(':height', $_POST['height']);
	if($stmt->execute()) {
		$userid = $dbh->lastInsertId();
		$stmt = $dbh->prepare('INSERT INTO locations (id, location) VALUES (:user_id, :location)');
		$locations = explode(',', $_POST['locations']);
		$stmt->bindParam(':user_id', $userid);
		$stmt->bindParam(':location', $location);
		foreach ($locations as $location) {
			$stmt->execute();
		}
		print json_encode(array('userid' => $userid));
	}
	else {
		print json_encode(array('error' => 'could not register'));
	}
}
catch(PDOException $e) {
	print json_encode(array('error' => '$e->getMessage()'));
}

// Begin file upload handling
//print_r($_FILES);
//echo "Gonna start"
$uploaddir = $photos;
if (!isset($_FILES)) {
	print json_encode(array('error' => 'No files recieved'));
} else {
	//echo "Got files";
	$trainingIds = array();
    foreach ($_FILES as $file) {
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
		$trainingFiles[] = $uploadpath;
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
	
	//echo "About to call the trainer<br>";
	// Now call the trainer on the files
	//train($userid,$trainingFiles);
}
?>