package com.example.demo.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;

import com.opencsv.CSVParser;
import com.opencsv.CSVReader;
import com.example.demo.*;
import com.example.demo.model.ExtractsModel;

@Configuration
public class CsvParser {
	
	
	
	public List<ExtractsModel> openCSVParser(InputStream csvfile) throws IOException {
		CSVReader reader = new CSVReader(new InputStreamReader(csvfile, "UTF-8"), 
				CSVParser.DEFAULT_SEPARATOR,CSVParser.DEFAULT_QUOTE_CHARACTER, 1);

		
		List <ExtractsModel> list = new ArrayList<ExtractsModel>();

		// read line by line
		String[] row = null;

		while ((row = reader.readNext()) != null) {
			ExtractsModel model = new ExtractsModel();
            model.setusername(row[0]);
            model.setuserid(row[1]);
            model.setcreationdate(row[2]);
            model.setlastlogindate(row[3]);
            model.setlastlogintime(row[4]);
            model.setcountry(row[5]);
            model.setapplication(row[6]);
            model.setyear(row[7]);
            //model.setField9(row[8]);
            list.add(model);
		}
		reader.close();
		return list;
	}

	public List<ExtractsModel> parseCSV(InputStream csvfile) {
		 String line = "";
	     String cvsSplitBy = ",";
	     List <ExtractsModel> list = new ArrayList<ExtractsModel>();
	     try {
			BufferedReader br = new BufferedReader(new InputStreamReader(csvfile, "UTF-8"));
	            while ((line = br.readLine()) != null) {

	                // use comma as separator
	                String[] row = line.split(cvsSplitBy);
	                ExtractsModel model = new ExtractsModel();
	                model.setusername(row[0]);
	                model.setuserid(row[1]);
	                model.setcreationdate(row[2]);
	                model.setlastlogindate(row[3]);
	                model.setlastlogintime(row[4]);
	                model.setcountry(row[5]);
	                model.setapplication(row[6]);
	                model.setyear(row[7]);
	                //model.setField9(row[8]);
	                list.add(model);
	                
	            }

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		return list;
	}
}
