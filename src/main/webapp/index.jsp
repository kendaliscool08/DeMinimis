<html>
<head>
<title>UPS Spring MVC</title>
</head>
<body>
	<br>
	<div style="text-align: center">
		<h2>ARL Application is Running</h2>
		<h3>BSIS Test</h3>
		<h4>
			<a href="./transactionquery/json?">@RequestMapping("/transactionquery/json")
			</a>
		</h4>
		<h4>
			<a href="./transactionquery/xml?">@RequestMapping("/transactionquery/xml")
			</a>
		</h4>
		<h4>
			<a
				href="./dailypaymentid/json?ctrycode=US&suborg=100&type=VC_DAILY_PAYMENT_TYPE_IND">@RequestMapping("/dailypaymentid/json")
			</a>
		</h4>
		<h4>
			<a
				href="./dailypaymentid/xml?ctrycode=US&suborg=100&type=VC_DAILY_PAYMENT_TYPE_IND">@RequestMapping("/dailypaymentid/xml")
			</a>
		</h4>
		<h4>
			<a href="http://localhost:8080/deminimis-ws/shipments?shipmentNumber=SN123&brn=BRN123&amountValue=123.00&invoiceNumber=INV123&importCountryCode=USA">Test DeMinims</a>
		</h4>
		<h4>
			<a href="http://localhost:8080/test-services/testdata">Test Data</a>
		</h4>
	</div>
</body>
</html>