<!doctype html>
<html lang="ru">
<head>
    <meta charset="utf-8">
    <title>Клиенты</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/4.1.0/css/bootstrap.min.css">
</head>

<body>
<div class="container text-center">
    <img src="peppa.png" style="width: 300px; height: auto; padding: 50px">
</div>
<div class="container">
    <div class="row justify-content-center">
        <h4>Создать клиента</h4>
        <br/></div>
    <div class="row justify-content-center">
        <form method="POST" action="/clients/save" novalidate>
            <div class="row justify-content-around">
                <div style="padding-right: 10px">
                    <input required type="text" id="clientNameTextBox" name="name"  value="Эрик Картман" placeholder="Имя">
                </div>
                <div style="padding-right: 10px">
                    <input required type="text" id="clientPhoneTextBox" name="phone"  value="8 800 555 35 35" placeholder="Телефон">
                </div>
                <div style="padding-right: 10px">
                    <input type="text" id="clientAdditionalPhoneTextBox" value="-" name="additionalPhone"  placeholder="Дополнительный телефон">
                </div>
                <div style="padding-right: 10px">
                    <input required type="text" id="clientStreetTextBox" value="Южнопарковая" name="street" placeholder="Улица">
                </div>
                <button type="submit">Клик!</button>
            </div>
        </form>
    </div>
    <h4>Список всех клиентов</h4>
    <br/>
    <table class="table table-bordered table-condensed table-striped">
        <thead class="thead-dark">
        <div class="col-4">
            <tr>
                <div class="row justify-content-between">
                    <th scope="col">id</th>
                    <th scope="col">name</th>
                    <th scope="col">phone</th>
                    <th scope="col">additional phone</th>
                    <th scope="col">street</th>
                </div>
            </tr>
        </div>
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
</div>
</div>
</div>
</body>
</html>
