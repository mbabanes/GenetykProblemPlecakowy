package plecagene;


public class Item
{
    private int weight;
    private int worth;
    private String name;

    
    public Item()
    {
        
    }
       
    public Item(int weight, int worth, String name)
    {
        this.weight = weight;
        this.worth = worth;
        this.name = name;
    }

  
    
    public int getWeight()
    {
        return weight;
    }

    public void setWeight(int weight)
    {
        this.weight = weight;
    }

    
    
    public int getWorth()
    {
        return worth;
    }

    public void setWorth(int worth)
    {
        this.worth = worth;
    }

    
    
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
