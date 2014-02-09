<?php
/*
	Use: POST userid do
	do = {get,kill}
*/
include('lib.php');
$dbh = new PDO($conn_str, $user, $password);
$maxTargets = 4;
function dump_target_info($dbh, $userid) {
	$stmt = $dbh->prepare('SELECT id,name,sex,age,race,height,location FROM users WHERE id = :id');
	$stmt->bindParam(':id', $userid);
	$stmt->execute();
	$row = $stmt->fetch(PDO::FETCH_ASSOC);
	//var_dump($dbh->errorInfo());
	//var_dump($row);
	$res = mysql_query("SELECT id FROM photos WHERE userid='$userid'");
	$photos = array();
	while($photo = mysql_fetch_array($res)) {
		$photos[] = build_link($photo['id']);	
	}
	$row['photos'] = $photos;
	return $row;
}

function build_link($id) {
	$link = "http://moneypicsapp.com/Assassin/server/photos/$id.bmp";
	return $link;
}
if(!isset($_REQUEST['userid']) || !isset($_REQUEST['do'])) {
	exit(json_encode(array('error' => 'Missing POST data')));
} else {
	$userid = $_REQUEST['userid'];
	if($_REQUEST['do'] == "get") {
		$stmt = $dbh->prepare('SELECT COUNT(userid) FROM targets WHERE hunter_id=:id');
		$stmt->bindParam(':id', $_REQUEST['userid']);
		$stmt->execute();
		if($stmt->fetchColumn() > 0) {
			$stmt = $dbh->prepare('SELECT userid FROM targets WHERE hunter_id=:id');
			$stmt->bindParam(':id', $_REQUEST['userid']);
			$stmt->execute();
			print json_encode(dump_target_info($dbh, $stmt->fetchColumn()));
		}
		else {
			$stmt = $dbh->query('SELECT users.id, COUNT(users.id) FROM users LEFT JOIN targets ON users.id = targets.userid GROUP BY users.id ORDER BY COUNT(users.id)');
			foreach($stmt as $row) {
				/* 				//print_r($row); */
				if($row['id'] != $_REQUEST['userid']) {
					$targetId = $row['id'];
					$stmt = $dbh->prepare('INSERT INTO targets (userid, hunter_id) VALUES (:targetId, :userid)');
					$stmt->bindParam(':targetId', $targetId);
					$stmt->bindParam(':userid', $_REQUEST['userid']);
					if(!$stmt->execute()) {
						print json_encode(array('error' => 'couldn\'t assign target!'));
					}
					else {
						//var_dump(dump_target_info($dbh,$userid));
						print json_encode(dump_target_info($dbh, $userid));
					}
					exit();
				}
			}
			print json_encode(array('error' => 'no possible targets!'));
		}
		// This means that they're asking who their target is. If they don't have one, assign one
	} else {
		// They're trying to kill someone, do a file upload and test
		$row = mysql_fetch_array(mysql_query("SELECT userid FROM targets WHERE hunter_id = '$userid'"));
		$target = $row['userid'];
		$predictId = $target; // This will be replaced with exec code and stuff. TODO
		if($predicted == $target) {
			mysql_query("DELETE FROM targets WHERE hunter_id='$userid'");
			exit(json_encode(array("success" => "Target Eliminated")));
		} else {
			// Check if it was their hunter
			$res = mysql_query("SELECT * FROM targets WHERE userid='$userid'");
			while($row = mysql_fetch_array($res)) {
				if($row['hunter_id'] == $predicted) {
					// They killed their hunter. 
					
				}
			}
		}
	}
}

?>
