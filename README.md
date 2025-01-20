# ReadMe

## Some conclutions

- It seems like not all APIs share the same filter or interceptor for key checking.
  - For example: 
    - /api/{culture}/collection
    - /api/{culture}/{object-number}
    - /api{culture}/collection/{object-number}/tiles
    - The response message is "Invalid key".
  - But for following:
    - /api/{culture}/usersets
    - /api/{culture}/usersets/{set-id}
    - The response message is "An invalid key is provided".

- It seems the RijksMuseum website is using AzureCDN.

- The entire website is not separated from the front and back ends
- By going through the performance test of the website, every 10 times of request got a response time almost 300 ms, and the others in almost 30 ms.
