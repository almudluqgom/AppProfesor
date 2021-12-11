<?php

define('HOST','192.168.1.14');
define('USER','root');
define('PASS','');
define('DB','dgpsanrafael');

$con = mysqli_connect(HOST,USER,PASS,DB);

$sql1 ="SELECT id,nombre,idfoto from profesor";

$result= mysqli_query($con,$sql1);
$res = array();

if($result){
    while($row= mysqli_fetch_array($result)){
        array_push($res, array('id' => $row['id'], 'nombre' => $row['nombre'],'idfoto' => $row['idfoto'])); 
    }
header('Content-Type: application/json');
echo json_encode($res);

}else{
    print("sin resultados");
}
mysqli_close($con);
?>