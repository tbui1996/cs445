package cs445.a1;

public class Profile implements ProfileInterface
{

	private String name;
	private String about;
	private Set<ProfileInterface> profile;
	
	public Profile()
	{
		this("","");
	}
	
	public Profile(String name, String about)
	{
		if(name == null)
		{
			this.name = "";
		}
		else
		{
			this.name = name;
		}
		if(about == null)
		{
			this.about = "";
		}
		else
		{
			this.about = about;
		}
		
		this.profile = new Set<ProfileInterface>();
	}
	
	@Override
	public void setName(String newName) throws IllegalArgumentException 
	{
		if(newName == null)
		{
			throw new java.lang.IllegalArgumentException("name is null");
		}
		else
		{
			this.name = newName;
		}
		
	}

	@Override
	public String getName() 
	{
		return this.name;
	}

	@Override
	public void setAbout(String newAbout) throws IllegalArgumentException 
	{
		if(newAbout == null)
		{
			throw new IllegalArgumentException("About is null");
		}
		else
		{
			this.about = newAbout;
		}
		
	}

	@Override
	public String getAbout() 
	{
		return this.about;
	}

	@Override
	public boolean follow(ProfileInterface other)
	{
		if(other == null)
		{
			return false;
		}
		try
		{
			return profile.add(other);
		}
		catch(SetFullException t)
		{
			System.out.println(t);
			return false;
		}
	}

	@Override
	public boolean unfollow(ProfileInterface other) 
	{
		if(profile.contains(other) || other != null)
		{
			profile.remove(other);
			return true;
		}
		return false;
	}

	@Override
	public ProfileInterface[] following(int howMany) 
	{
		Object[] temp = profile.toArray();
		ProfileInterface[] followArr;
		if(temp.length < howMany)
		{
			followArr = new ProfileInterface[temp.length];
		}
		else
		{
			followArr = new ProfileInterface[howMany];
		}
		
		for(int k = 0; k < followArr.length; k++)
		{
			if(temp[k] != null)
			{	
				followArr[k] = (ProfileInterface) temp[k];
			}	
		}
		
		return followArr;
	}

	@Override
	public ProfileInterface recommend() 
	{
		ProfileInterface[] friend;
		ProfileInterface[] mutualFriend;
		ProfileInterface f = null;
		ProfileInterface mF = null;
		
		if(!profile.isEmpty())
		{
			friend = this.following(1000);
			for(int k = 0; k < friend.length; k++)
			{
				if(friend[k] != this)
				{
					f = friend[k];
				}
			}
			
			mutualFriend = f.following(1000);
			if(mutualFriend.length > 0)
			{
				for(int k = 0; k < mutualFriend.length; k++)
				{
					mF = mutualFriend[k];
					if(mF != this && profile.contains(mF) == false)
					{
						mF = mutualFriend[k];
						return mF;
					}
				}	
				return null;
			}
			else
			{
				return null;
			}
		}
		else
		{
			return null;
		}
		
	}

}
