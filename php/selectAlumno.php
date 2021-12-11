<?php
error_reporting(0);
define('HOST','192.168.1.14');
define('USER','root');
define('PASS','');
define('DB','dgpsanrafael');

$id=$_GET["id"];

$con = mysqli_connect(HOST,USER,PASS,DB);

$sql1 ="SELECT Nombre from Alumno where id = $id";

$result= mysqli_query($con,$sql1);
$res = array();

if($result){
    while($row= mysqli_fetch_array($result)){

        array_push($res, array(
            'nombre' => $row['Nombre']
        )); 
    }
header('Content-Type: application/json');
echo json_encode($res);

}else{
    print("sin resultados");
}
mysqli_close($con);
?>