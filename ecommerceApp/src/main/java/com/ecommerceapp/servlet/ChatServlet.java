package com.ecommerceapp.servlet;

import com.ecommerceapp.security.Encryption;
import com.ecommerceapp.security.KeyExchange;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.crypto.KeyAgreement;
import java.security.Key;
import java.security.KeyPair;

@WebServlet("chat")
public class ChatServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		KeyPair aliceKey = KeyExchange.generateKeys();
		KeyPair bobKey = KeyExchange.generateKeys();
		KeyAgreement aliceAgreement = KeyExchange.generateAgreement(aliceKey.getPrivate());
		KeyAgreement bobAgreement = KeyExchange.generateAgreement(bobKey.getPrivate());
		Key sharedKey1 = KeyExchange.generateSymmetricKey(aliceAgreement, bobKey.getPublic());
		Key sharedKey2 = KeyExchange.generateSymmetricKey(bobAgreement, aliceKey.getPublic());
		String binaryKey = KeyExchange.toBinaryString(sharedKey1);
		String encrypted = Encryption.encryptECB("This is a test", binaryKey);
	}
}
