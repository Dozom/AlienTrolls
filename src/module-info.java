module AlienTrolls {
	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.media;
	requires javafx.swing;
	requires javafx.web;	
	requires java.sql;
	
	opens controller;
	opens RankingViews to javafx.base;
	
}
