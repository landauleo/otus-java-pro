<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Клиенты</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
</head>

<body>
<img src="../static/img/peppa.png" style="width: 300px; height: 300px">
<h4>Создать клиента</h4>
<form method="post" action="/save" novalidate>
    <input required type="text" id="clientNameTextBox" value="test" placeholder="Имя">
    <input required type="text" id="clientPhoneTextBox" value="test" placeholder="Телефон">
    <input type="text" id="clientAdditionalPhoneTextBox" value="test" placeholder="Дополнительный телефон">
    <input required type="text" id="clientStreetTextBox" value="test" placeholder="Улица">
    <button type="submit">СОТВОРИТЬ</button>
</form>
<h4>Список всех клиентов</h4>
<table class="table">
    <thead class="thead-dark">
    <tr>
        <th scope="col">id</th>
        <th scope="col">name</th>
        <th scope="col">phone</th>
        <th scope="col">additional phone</th>
        <th scope="col">street</th>
    </tr>
    </thead>
    <tbody>
    <#list clients as client>
        <tr>
            <#if client.id??>
                <th>${client.id}</th></#if>
            <#if client.name??>
                <th>${client.name}</th></#if>
        </tr>
    </#list>
</table>
</body>
</html>
