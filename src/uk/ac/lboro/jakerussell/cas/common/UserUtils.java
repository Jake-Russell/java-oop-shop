package uk.ac.lboro.jakerussell.cas.common;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import uk.ac.lboro.jakerussell.cas.data.User;

/**
 * UserUtils is responsible for the common user functionality between the CLI
 * and GUI applications
 * 
 * @author Jake Russell
 * @version 1.0
 * @since 01/03/2020
 */
public class UserUtils {

	/**
	 * Sorts users by their user ID given a set of the users to be sorted
	 * 
	 * @param users the set of users to be sorted
	 * @return the list of users, sorted
	 */
	public static List<User> sortUsersByUserID(Set<User> users) {
		List<User> sortedUsers = new ArrayList<>(users);
		sortedUsers.sort(new Comparator<User>() {

			@Override
			public int compare(User user1, User user2) {
				if (user1.getUserID() == user2.getUserID()) {
					return 0;
				} else if (user1.getUserID() > user2.getUserID()) {
					return 1;
				}
				return -1;
			}

		});
		return sortedUsers;
	}
}
