<?php
	include_once('lib.php');
	include_once('gcm.php');
	if(!array_key_exists('userid', $_POST)) {
		exit(json_encode(array('error' => 'usage: post userid')));
	}
	$userid = $_POST['userid'];
	try {
		$dbh = new PDO($conn_str, $user, $password);
		//$result = messageUser($dbh, $_POST['userid']);
		//print json_encode(array("result" => $result['success']));

		if(array_key_exists('powerup', $_POST)) {
			$powerup = $_POST['powerup'];
			if($powerup == 0) {
				print 'Invisibility Powerup Activated';
				// Find and message all hunters
				$sql = "SELECT * FROM targets WHERE userid = '$userid'";
				$res = mysql_query($sql);
				while($row = mysql_fetch_assoc($res)) {
						echo "IT GOT CALLED";
						messageUser($dbh, $row['hunter_id'], array("msg" => "INVISIBILITY"));
				}
			} 
			else if($powerup == 1) {
				print 'Radar Powerup Activated';
				$row = mysql_fetch_array(mysql_query("SELECT * FROM targets WHERE hunter_id = '$userid'"));
				$target = $row['userid'];
				messageUser($dbh, $target, array("msg" => "RADAR"));
			}
		}
	}
	catch(Exception $e) {
		print json_encode(array('error' => $e->getMessage()));
	}
?>
