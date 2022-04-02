package com.lilithsthrone.rebelbase;

public enum Quest implements com.lilithsthrone.game.character.quests.Quest {
	HANDLE_REFUSED,
	PASSWORD_PART_ONE,
	PASSWORD_PART_TWO,
	PASSWORD_COMPLETE,
	EXPLORATION,
	ESCAPE,
	FAILED,
	FIREBOMBS_START,
	FIREBOMBS_FINISH,
	FIREBOMBS_FAILED;

	@Override
	public String getName() {
		switch(this) {
		case HANDLE_REFUSED:
			return "Hit The Lever!";
		case PASSWORD_PART_ONE:
			return "Pull Handle, Receive Mystery";
		case PASSWORD_PART_TWO:
			return "Fill In The Blank";
		case PASSWORD_COMPLETE:
			return "Open Says Me";
		case EXPLORATION:
			return "Going Spelunking";
		case ESCAPE:
			return "Getting Away With It";
		case FAILED:
			return "Cold Feet";
		case FIREBOMBS_START:
			return "Breaking Bombs";
		case FIREBOMBS_FINISH:
			return "Fire From The Rats";
		case FIREBOMBS_FAILED:
			return "Roxy's Reluctance";
		}
		throw new UnsupportedOperationException();
	}

	@Override
	public String getDescription() {
		switch(this) {
		case HANDLE_REFUSED:
			return "You found a strange handle in the Bat Caverns. Who knows what it's for or what it does.";
		case PASSWORD_PART_ONE:
			return "The strange handle asked for some kind of password that you don't have. Perhaps a bit of searching in the vicinity will reveal some clues.";
		case PASSWORD_PART_TWO:
			return "The other half of the password must be on the other half of the journal page. Perhaps it can still be found nearby.";
		case PASSWORD_COMPLETE:
			return "With the completed password, you can now give the handle's challenge another try.";
		case EXPLORATION:
			return "No telling what the hidden cave is for or where it goes. Perhaps you can find some answers inside.";
		case ESCAPE:
			return "Time to make your escape, preferably before this cave collapses in on itself.";
		case FAILED:
			return "You managed to get out in one piece, but whatever secrets that cave held are now buried forever.";
		case FIREBOMBS_START:
			return "The firebombs you got from the mysterious cave could come in handy in a fight. You'll need to find someone who can either make or acquire more.";
		case FIREBOMBS_FINISH:
			return "It will take Roxy two days to get a new supply of firebombs going. You should return to her then.";
		case FIREBOMBS_FAILED:
			return "Without an example to give to Roxy, she either couldn't understand or didn't want to be bothered with trying to replicate the firebombs you found...";
		}
		throw new UnsupportedOperationException();
	}

	@Override
	public String getCompletedDescription() {
		switch(this) {
		case HANDLE_REFUSED:
			return "You pulled the handle against your better judement.";
		case PASSWORD_PART_ONE:
			return "You found half of a journal page that mentioned that the password is two words. You could only decipher one word, the other got torn away.";
		case PASSWORD_PART_TWO:
			return "You found both halves of the password, together they make the phrase 'RUAT CAELUM'";
		case PASSWORD_COMPLETE:
			return "You discovered that the handle was actually attached to a door that led to a hidden cave splitting off from the Bat Caverns.";
		case EXPLORATION:
			return "You found out that the hidden cave was a hideout for a long gone rebel group. From the looks of things, they didn't win.";
		case ESCAPE:
			return "You managed to get out in one piece. Whatever else that cave held is now buried forever.";
		case FIREBOMBS_START:
			return "Roxy has agreed to look into getting more firebombs for you.";
		case FIREBOMBS_FINISH:
			return "Somehow, Roxy didn't swindle you and you've gotten yourself a supply of firebombs.";
		default:
			return getDescription();
		}
	}

	@Override
	public int getLevel() {
		switch(this) {
		case FIREBOMBS_START:
		case FIREBOMBS_FINISH:
		case FIREBOMBS_FAILED:
			return 1;
		default:
			return 15;
		}
	}

	@Override
	public int getExperienceReward() {
		switch(this) {
		case ESCAPE:
			return 100;
		case FAILED:
		case FIREBOMBS_FAILED:
			return 0;
		default:
			return 5;
		}
	}
}
