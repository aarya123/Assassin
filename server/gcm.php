<?php
	include_once('lib.php');
	$dbh = new PDO($conn_str, $user, $password);
//$res = messageUser($dbh, 60, array("msg" => "Hello!"));
//var_dump();

	function messageUser($dbh, $user_id, $data = false) {
		$api_key = 'AIzaSyBWg07tBw9TSepbV58qIUETrVreaV-Tvko';
		$stmt = $dbh->prepare('SELECT registration_id FROM users WHERE id = :user_id');
		$stmt->bindParam(':user_id', $user_id);
		$stmt->execute();
		if($stmt->rowCount() == 0) {
			throw new Exception('user_id doesn\'t exist!');
		}
		$registration_id = $stmt->fetchColumn();
		$ch = curl_init();
		curl_setopt($ch, CURLOPT_POST, 1);
		curl_setopt($ch, CURLOPT_URL, 'https://android.googleapis.com/gcm/send');

		curl_setopt($ch, CURLOPT_HTTPHEADER, array(
			'Authorization:key=' . $api_key,
			'Content-Type: application/json')
		);
		curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
		$message = array(
			'registration_ids' => array($registration_id)
		);
		if($data) {
			$message['data'] = $data;
		}
		//echo json_encode($message);
		curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($message));
		curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
		curl_setopt($ch, CURLOPT_CONNECTTIMEOUT_MS, 2000);
		curl_setopt($ch, CURLOPT_TIMEOUT_MS, 2000);
		
		$response = curl_exec($ch);
		//var_dump($response);
		//echo $response;
		$response_code = curl_getinfo($ch, CURLINFO_HTTP_CODE);
		if(!$response) {
			throw new Exception(curl_error($ch));
		}
		else if($response_code != 200) {
			throw new Exception('gcm returned ' . $response_code);
		}
		else {
			
			$result = json_decode($response, true);
			if($result['failure'] > 0) {
				throw new Exception($result['results'][0]['error']);
			}
			return $result;
		}
	}
?>