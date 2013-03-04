package se.mobileapplab.listdemo;

import java.io.Serializable;

import android.annotation.SuppressLint;

@SuppressLint("DefaultLocale")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public String name;
	public int price;
	
	@Override
	public String toString() {
		return String.format("%s %d SEK", name, price);
	}
}
