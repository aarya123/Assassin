<?php
/*
	Use: POST userid do
	do = {get,kill}
*/
include('lib.php');
$dbh = new PDO($conn_str, $user, $password);
$maxTargets = 4;
function dump_target_info($dbh, $userid) {
	$stmt = $dbh->prepare('SELECT id,sex,age,race,height FROM users WHERE id = :id');
	$stmt->bindParam(':id', $userId);
	$stmt->execute();
	return $stmt->fetch(PDO::FETCH_ASSOC);

}
if(!isset($_REQUEST['userid']) || !isset($_REQUEST['do'])) {
	exit(json_encode(array('error' => 'Missing POST data')));
} else {
	$userid = $_REQUEST['userid'];
	if($_REQUEST['do'] == "get") {
		$stmt = $dbh->prepare('SELECT COUNT(userid) FROM targets WHERE id=:id');
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
					$stmt = $dbh->prepare('INSERT INTO targets (userid, hunter_id) VALUES (:targetId, :userId)');
					$stmt->bindParam(':targetId', $targetId);
					$stmt->bindParam(':userId', $_REQUEST['userid']);
					if(!$stmt->execute()) {
						print json_encode(array('error' => 'couldn\'t assign target!'));
					}
					else {
						print json_encode(dump_target_info($dbh, $userId));
					}
					exit();
				}
			}
			print json_encode(array('error' => 'no possible targets!'));
		}
		// This means that they're asking who their target is. If they don't have one, assign one
	} else {
		// They're trying to kill someone, do a file upload and test
	}
}

?>
