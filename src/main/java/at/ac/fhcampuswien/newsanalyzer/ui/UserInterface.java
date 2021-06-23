package at.ac.fhcampuswien.newsanalyzer.ui;


import at.ac.fhcampuswien.newsanalyzer.ctrl.Controller;
import at.ac.fhcampuswien.newsanalyzer.ctrl.NewsAPIException;
import at.ac.fhcampuswien.newsanalyzer.downloader.Downloader;
import at.ac.fhcampuswien.newsanalyzer.downloader.ParallelDownloader;
import at.ac.fhcampuswien.newsanalyzer.downloader.SequentialDownloader;
import at.ac.fhcampuswien.newsapi.*;
import at.ac.fhcampuswien.newsapi.beans.*;
import at.ac.fhcampuswien.newsapi.enums.*;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class UserInterface extends Downloader
{
	private static final String APIKEY = "48fab5bfdd0746208746311d65815d08";
	private Controller ctrl = new Controller();

	public void getDataFromCtrl1(){

		NewsApi newsApi = new NewsApiBuilder()
				.setApiKey(APIKEY)
				.setQ("corona")
				.setEndPoint(Endpoint.TOP_HEADLINES)// example of how to use enums
				.setSourceCountry(Country.at)       // example of how to use enums
				.setSourceCategory(Category.health) // example of how to use enums
				.createNewsApi();

		ctrl.process(newsApi);


	}

	public void getDataFromCtrl2(){
		// TODO implement me Finished


		NewsApi newsApi = new NewsApiBuilder()
				.setApiKey(APIKEY)
				.setQ("microsoft")
				.setEndPoint(Endpoint.TOP_HEADLINES)// example of how to use enums
				.setSourceCountry(Country.at)       // example of how to use enums
				.setSourceCategory(Category.technology) // example of how to use enums
				.createNewsApi();

		ctrl.process(newsApi);


	}

	public void getDataFromCtrl3(){
		// TODO implement me Finished


		NewsApi newsApi = new NewsApiBuilder()
				.setApiKey(APIKEY)
				.setQ("corona")
				.setEndPoint(Endpoint.TOP_HEADLINES)// example of how to use enums
				.setSourceCountry(Country.at)       // example of how to use enums
				.setSourceCategory(Category.science) // example of how to use enums
				.createNewsApi();

		ctrl.process(newsApi);


	}

	public void getDataForCustomInput() { //user filter
		// TODO implement me Finished

		Scanner s = new Scanner(System.in);

		for (Category categories:Category.values()){
			System.out.println(categories);
		}
		System.out.println("Category: ");
		String category = s.next();
		System.out.println("Country: ");
		String country = s.next();
		System.out.println("Topic: ");
		String topic = s.next();
		for(Category cat: Category.values()){
			//System.out.println(cat);
			if(category.equals(cat.toString())) {
				NewsApi newsApi = new NewsApiBuilder()
						.setApiKey(APIKEY)
						.setQ(topic)
						.setEndPoint(Endpoint.TOP_HEADLINES)// example of how to use enums
						.setSourceCountry(Country.valueOf(country))       // example of how to use enums
						.setSourceCategory(Category.valueOf(category)) // example of how to use enums
						.createNewsApi();

				ctrl.process(newsApi);
				break;
			}
		}



	}
	public void getDataFromCtrl4(){//new
		// TODO implement me Finished
		try {
			process(ctrl.urlList);
		}catch (Exception e){
			System.err.println(e.getMessage());
		}

	}

	public void getDataFromCtrl5(){//new
		// TODO implement me Finished
		try {
			ParallelDownloader pd = new ParallelDownloader();
			pd.run();
		}catch (Exception e){
			System.err.println(e.getMessage());
		}
	}

	public void getDataFromCtrl6(){//new
		// TODO implement me Finished
		try {
			SequentialDownloader sd = new SequentialDownloader();
			sd.process(Controller.urlList);
		}catch (Exception e){
			System.err.println(e.getMessage());
		}

	}



	public void start() {
		Menu<Runnable> menu = new Menu<>("User Interface");
		menu.setTitle("Wählen Sie aus:");
		menu.insert("a", "Choice: Corona Health", this::getDataFromCtrl1);
		menu.insert("b", "Choice: Microsoft", this::getDataFromCtrl2);
		menu.insert("c", "Choice: Corona Science", this::getDataFromCtrl3);
		menu.insert("d", "Choice User Input:",this::getDataForCustomInput);
		menu.insert("e", "Download last search",this::getDataFromCtrl4);
		menu.insert("f", "Parallel Downloader",this::getDataFromCtrl5);
		menu.insert("g", "Sequential Downloader",this::getDataFromCtrl6);
		menu.insert("q", "Quit", null);
		Runnable choice;
		while ((choice = menu.exec()) != null) {
			choice.run();
		}
		System.out.println("Program finished");
	}




	protected String readLine() {
		String value = "\0";
		BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
		try {
			value = inReader.readLine();
		} catch (IOException ignored) {
		}
		return value.trim();
	}

	protected Double readDouble(int lowerlimit, int upperlimit) 	{
		Double number = null;
		while (number == null) {
			String str = this.readLine();
			try {
				number = Double.parseDouble(str);
			} catch (NumberFormatException e) {
				number = null;
				System.out.println("Please enter a valid number:");
				continue;
			}
			if (number < lowerlimit) {
				System.out.println("Please enter a higher number:");
				number = null;
			} else if (number > upperlimit) {
				System.out.println("Please enter a lower number:");
				number = null;
			}
		}
		return number;
	}
	public int process(List<String> urls){//new
		try {
			saveUrl2File(urls.get(urls.size() - 1));

		}catch(Exception e){
			System.err.println(e.getMessage());
		}
		return 0;
	}
	public Object Data(){return null;}
}
