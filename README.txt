Operations tested with Postman: 

GET: http://localhost:8080/api/v1/accounts
Gets a list of all accounts and their balances 

POST: http://localhost:8080/api/v1/accounts
body: {"id":"asfdf9","balance":10000}
Creates an account with id asdff9 with initial balance 10000

PUT: http://localhost:8080/api/v1/accounts/asfdf9
body: {"id":"asfdf9","balance":3000} 
Is used to edit an account. But this is not really used or applicable in the situation.

GET: http://localhost:8080/api/v1/accounts/highest
Gets the account with the highest funds. 

GET: http://localhost:8080/api/v1/transfers 
Gets all of the transfers.

POST: http://localhost:8080/api/v1/transfers
Creates a transaction from one account to another.

GET: http://localhost:8080/api/v1/transfers/dateFrom/2019-08-26/dateTo/2019-08-29
Gets all the transfers by a specific date range. 

GET: http://localhost:8080/api/v1/transfers/source/frequent
Gets the most frequently used account with its id and balance.

Some junit tests are also included

