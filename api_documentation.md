🚀 SmartSpend API Documentation
Base URL: http://localhost:8080/api/expenses

Content-Type: application/json

1. Add AI-Powered Expense
This endpoint takes a natural language description and uses the ExpenseAiService (powered by Llama-3.3-70b) to automatically categorize the transaction.

Endpoint: POST /add

Query Parameters:

description (String): e.g., "Uber ride to the office"

amount (Double): e.g., 500.0

Response:

JSON
{
  "id": 1,
  "description": "Uber ride to the office",
  "amount": 500.0,
  "category": "TRAVEL",
  "date": "2024-03-21"
}
2. Get All Expenses
Retrieves a complete list of all transactions stored in the database.

Endpoint: GET /all

Success Response: A JSON array of all expense objects.

3. Dashboard Statistics (Quick Range)
Fetches aggregated data (Total Spent, Top Category, and Item Count) for predefined time windows.

Endpoint: GET /stats

Query Parameters:

range (String): Accepts week or month.

Response Example:

JSON
{
  "totalSpent": 1500.0,
  "topCategory": "FOOD",
  "itemCount": 5,
  "list": [...] 
}
4. Advanced Filter & Search
A flexible endpoint used by the frontend to filter results by specific dates and categories.

Endpoint: GET /filter

Optional Query Parameters:

startDate (String): YYYY-MM-DD

endDate (String): YYYY-MM-DD

category (String): Filter by specific category (e.g., "SHOPPING")

5. Delete Expense
Removes an expense record from the system by its unique ID.

Endpoint: DELETE /delete/{id}

Path Variable: id (Long)
