package com.openclassrooms.tourguide.helper;

public class InternalTestHelper {

	// Set this default up to 100,000 for testing.
	private static int internalUserNumber = 100;
	
	// Modifie le nombre d'utilisateurs pour les test.
	public static void setInternalUserNumber(int internalUserNumber) {
		InternalTestHelper.internalUserNumber = internalUserNumber;
	}
	
	// Récupère le nombre d'utilisateurs.
	public static int getInternalUserNumber() {
		return internalUserNumber;
	}
}
