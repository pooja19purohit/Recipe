package com.recipe.command;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import com.recipe.mongo.ConnectionProvider;


public class CreateImageCommand {
	
	public boolean execute() {
		MongoClient client = null;
		
		try {
		client = (new ConnectionProvider()).getConnection();
		DB mdb = client.getDB("recipe");
		
		 String dbFileName = "DaalTadka";
		    File imageFile = new File("/Users/pooja/Desktop/Dal-Soup.jpg");
		    GridFS gfsPhoto = new GridFS(mdb, "images");
		    GridFSInputFile gfsFile = gfsPhoto.createFile(imageFile);
		    gfsFile.setFilename(dbFileName);
		    gfsFile.put("recipeLink", "DalTadka");
		    gfsFile.save();
		}
		catch(Exception e) {
			return false;
		}
		finally{
			client.close();
		}
		    return true;
	}

}
