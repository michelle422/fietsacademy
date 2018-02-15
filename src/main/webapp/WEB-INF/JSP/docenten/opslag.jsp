<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@taglib uri='http://java.sun.com/jsp/jstl/fmt' prefix='fmt'%>
<%@taglib uri='http://vdab.be/tags' prefix='v'%>
<!doctype html>
<html lang='nl'>
<head>
	<v:head title="Opslag"/>
</head>
<body>
	<v:menu/>
	<h1>Opslag</h1>
	<form method="post" id="opslagform">
		<label>
			Percentage:<span>${fouten.percentage}</span>
			<input name="percentage" value="${param.percentage}" required autofocus type="number" min="0.01" step="0.01">
		</label>
		<input type="submit" value="Opslag" id="opslagknop">
	</form>
	<script>
		document.getElementById('opslagform').onsubmit = function() {
			document.getElementById('opslagknop').disabled = true;
		}
	</script>
</body>
</html>