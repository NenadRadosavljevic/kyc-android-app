# kyc-android-app
This App shows integration with the Passbase KYC provider using their Android SDK.
## Instructions for metadata
In the ID verification session, a metadata is sent along with the personal data set from the user identification document.
Dataset needs to be serialized as JSON object and then encrypted. There are only 245 bytes for JSON structure as a plaintext. And max 245 bytes can be input for Passbase encryption algorithm. 
```
{
    "note": "I am testing adding metadata to ID verification.",
    "email": "nenad@email.com",
    "country": "rs",
    "customer_id": "UID of Nenad"
}
```
Per Passbase documentation encryption algorithm is RSA 4096 bits, but that was not implemented in the Passbase server side. It's RSA 2048 bits with PKCS1Padding.
Complete spec is "RSA/ECB/PKCS1Padding". Use openssl tool to create RSA keypair (private and pub) and they must be 2048bits and in PEM format.
Handling metadata and encryption is implemented in the class aDocumentData.

