import java.util.HashMap;

public class midterm2Q3 {
    public static void main(String[] args){
        System.out.println("Enjoying the Enhanced Map");
        EnhancedMap<String,Integer> em = new EnhancedMap<String,Integer>();
        em.put("Ahmet", 15);
        em.put("Ahmet", 20);
        em.put("Ahmet", 67);
        System.out.println(em.get("Ahmet"));
    }
}

class EnhancedMap <K, V> extends HashMap <K, V>{

    @Override
    public V put(K key, V value) {
        return super.put(key, value);
    }

    @Override
    public V get(Object key) {
        return super.get(key);
    }
}
