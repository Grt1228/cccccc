package com.unfbx.demo.file;

public class Main {

	public static void main(String[] args) {
		BigFileReader.Builder builder = new BigFileReader.Builder("d:/reliability.txt",new IHandle() {
			
			@Override
			public void handle(String line) {
				System.out.println(line);
				//increat();
			}
		});
		builder.withThreadSize(10)
			   .withCharset("utf-8")
			   .withBufferSize(1024*1024);
		BigFileReader bigFileReader = builder.build();
		bigFileReader.start();
	}
	
}
