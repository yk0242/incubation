<html>
<head></head>
<body>
<h1>Server Stub</h1>
server stub to receive post contents and write them to text<br>

<?php
file_put_contents("logs/post.log", print_r($_REQUEST, true), FILE_APPEND);
print_r($_REQUEST);
?>

</body>
</html>
