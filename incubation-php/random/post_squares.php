<html>
<head></head>
<body>
<h1>hello world, it works! </h1>
test<br>
<form action="<?php $_SERVER['PHP_SELF'] ?>" method="post">
	upper lim: <input type="text" value="5" name="upper"><br>
	<input type="submit" value="output">
</form>
<?php 
$upper = 1;
if(isset($_POST['upper'])){
	$upper = $_POST['upper'];
}
for($i=0; $i<=$upper; $i++){
	echo pow($i,2);
	if($i==$upper) break;
	echo ', ';
}
echo '<br>';
if(null==false)echo 'null==false';
if(null===false)echo 'null===false';
?>

</body>
</html>
