package at.ac.fhcampuswien.newsanalyzer.downloader;

import at.ac.fhcampuswien.newsanalyzer.ctrl.NewsAPIException;

import java.util.List;

public class SequentialDownloader extends Downloader {

    @Override
    public int process(List<String> urls) throws DownloaderException, NewsAPIException {
        int count = 0;
        long start = System.nanoTime();//new
        for (String url : urls) {
            String fileName = saveUrl2File(url);
            if(fileName != null)
                count++;
        }
        long end = System.nanoTime();//new
        System.out.println("Time: "+ (end-start)/1000000 + " milliseconds");
        return count;
    }
}
