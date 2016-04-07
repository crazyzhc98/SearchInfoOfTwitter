package Test;

import java.util.ArrayList;
import java.util.List;

import twitter4j.FilterQuery;
import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.Query.ResultType;
import twitter4j.Query.Unit;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class Test {

	private static Twitter twitter;
	private static TwitterStream twitterStream;
	private static AccessToken accessToken;
	private static QueryResult result;

	public static void main(String[] args) {

		ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
		configurationBuilder.setOAuthConsumerKey("gRXAt1SLcEy1yr3WzMsTEO3g5"); // set consumerKey
		configurationBuilder.setOAuthConsumerSecret("sCLjKhbWIy6JTJTaC7wsHGDTNEN2depqnUzfK8CKnA0qN6frGI"); // set consumerSecret
		configurationBuilder.setJSONStoreEnabled(true);
		Configuration configuration = configurationBuilder.build();
		twitter = new TwitterFactory(configuration).getInstance();
		TwitterStreamFactory tf = new TwitterStreamFactory(configuration);
		twitterStream = tf.getInstance();
		accessToken = new AccessToken("3884452454-IikWuHKIIlaIeB0cwQx7EJioIQVkBD0lP1vVuxL",
				"hLna6TOtP7uUnKc1AcLTXisSkBRe47HnfFW5y5cbda0XU"); // set access token
		System.out.println("AccessToken:" + accessToken);
		twitter.setOAuthAccessToken(accessToken); // twitter post tweets
		twitterStream.setOAuthAccessToken(accessToken); // twitterStream listen tweets
//		try {
//			List<User> users = twitter.searchUsers("Crazy", 3);
//			
//			for(User user:users){
//				System.out.println("UserName: "+user.getName());
//			}
//		} catch (TwitterException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		GeoLocation location = new GeoLocation(37.2988604,-120.5414307);
		
//		List<TwitterInfo> infos = search(new Query("near:\"Merced, CA\" within:15mi :)"));
		List<TwitterInfo> infos = search("", 100, null, null, location);
		
		
		// twitterStream.addListener(listener); // 这里加入了listener用于处理侦听推文
		// FilterQuery query = new FilterQuery();
		// query.follow(new long[] { userId }); // userId 是在Twitter App页面中的用户ID
		// twitterStream.filter(query);
	}

	public static List<TwitterInfo> search(Query query) {
		List<TwitterInfo> infos = new ArrayList<>();
		try {
			do {
				result = twitter.search(query);
				List<Status> tweets = result.getTweets();
				String num = result.getQuery();
				int level = result.getAccessLevel();
				infos.addAll(res2TwitterInfos(tweets));
				if (infos.size() >= query.getCount())
					break;
			} while ((query = result.nextQuery()) != null);
		} catch (TwitterException te) {
			te.printStackTrace();
		}
		return infos;

	}

	public static List<TwitterInfo> search(String key, int count, String sinceDate, String endDate,
			GeoLocation location) {
		List<TwitterInfo> infos = new ArrayList<>();

		Query query = new Query();
		try {
			query.setResultType(ResultType.mixed);
			query.setQuery(key);
			query.setCount(100);// Set count per query
			if (location != null) {
				query.setGeoCode(location, 15, Unit.mi);
			}

			if (sinceDate != null) {
				query.setSince(sinceDate);
			}
			if (endDate != null) {
				query.until(endDate);
			}
			do {
				result = twitter.search(query);
				List<Status> tweets = result.getTweets();
				String num = result.getQuery();
				int level = result.getAccessLevel();
				infos.addAll(res2TwitterInfos(tweets));
				if (infos.size() >= count)
					break;
			} while ((query = result.nextQuery()) != null);
		} catch (TwitterException te) {
			te.printStackTrace();
		}

		return infos;
	}

	public static List<TwitterInfo> res2TwitterInfos(List<Status> tweets) {
		TwitterInfo info = null;
		List<TwitterInfo> infos = new ArrayList<TwitterInfo>();
		int i=0;
		for (Status tweet : tweets) {
			info = new TwitterInfo();
			info.setId(String.valueOf(tweet.getId()));
			info.setContent(tweet.getText());
			info.setDate(tweet.getCreatedAt());
			info.setClient(tweet.getSource());
			if(tweet.getPlace()!=null){
				info.setPlace(tweet.getPlace().getFullName());
			}
			infos.add(info);
			System.out.print(i+++"@");
			info.print();
		}
		return infos;

	}

}
