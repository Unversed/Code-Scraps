import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Popularity {
   static final int maxGap = 1000000;
   static final int topMost = 25;
   static Random idGap;
   
   static public class Customer {
      public static long nextId = 0;
      public long id;
      public String firstName;
      public String lastName;
      
      public Customer(String fName, String lName) {
         nextId += idGap.nextInt(maxGap);
         id = nextId;
         firstName = fName;
         lastName = lName;
      }
   }

   static public class Receipt {
      public static long nextId = 0;
      public long id;
      public long customerId;
      
      public Receipt(long cId) {
         nextId += idGap.nextInt(maxGap);
         id = nextId;
         customerId = cId;
      }      
   }
   
   static public class LineItem {
      public long receiptId;
      public String productId;
      
      public LineItem(long rId, String pId) {
         receiptId = rId;
         productId = pId;
      }
   }
   
   static public class Product {
      public String id;
      public String kind;
      public String flavor;
      
      public Product(String id, String kind, String flavor) {
         this.id = id;
         this.kind = kind;
         this.flavor = flavor;
      }  
   }
   
   

static public class ProductPopularity
      implements Comparable<ProductPopularity> {
   public String productId;
   public HashSet<Long> popularity;

   public ProductPopularity(String product, HashSet<Long> set) {
      this.productId = product;
      this.popularity = set;
   }

   @Override
   public int compareTo(ProductPopularity product) {
      return (this.popularity.size() > (product.popularity.size()) ? -1
         : (this.popularity.size() < product.popularity.size()) ? 1
         : this.productId.compareTo(product.productId));
   }
}

static public class CustomerDiversity
      implements Comparable<CustomerDiversity> {
   public Long customerId;
   public HashSet<String> diversity;

   public CustomerDiversity(Long customer, HashSet<String> set) {
      this.customerId = customer;
      this.diversity = set;
   }

   @Override
   public int compareTo(CustomerDiversity customer) {
      return (this.diversity.size() > (customer.diversity.size()) ? -1
         : (this.diversity.size() < customer.diversity.size()) ? 1
         : this.customerId.compareTo(customer.customerId));
   }
}

private static void analyzeSales(List<Customer> customers,
      List<Receipt> receipts, List<LineItem> lineItems,
      List<Product> products) {
   Map<Long, Long> rIdToCId = new HashMap<Long, Long>();
   Map<String, HashSet<Long>> pIdToCId = 
         new HashMap<String, HashSet<Long>>();
   Map<String, HashSet<Long>> pIdToRId = 
         new HashMap<String, HashSet<Long>>();
   Map<Long, HashSet<String>> cIdToPId = 
         new HashMap<Long, HashSet<String>>();
   for (Customer currentCustomer : customers) {
      cIdToPId.put(currentCustomer.id, new HashSet<String>());
   }
   for (Product product : products) {
      pIdToCId.put(product.id, new HashSet<Long>());
      pIdToRId.put(product.id, new HashSet<Long>());
   }
   for (Receipt receipt : receipts) {
      rIdToCId.put(receipt.id, receipt.customerId);
   }
   for (LineItem currentItem : lineItems) {
      pIdToCId.get(currentItem.productId)
         .add(rIdToCId.get(currentItem.receiptId));
   }

   for (String product : pIdToRId.keySet()) {
      for (Long receipt : pIdToRId.get(product)) {
         pIdToCId.get(product).add(rIdToCId.get(receipt));
      }
   }
   for (String product : pIdToCId.keySet()) {
      for (Long customer : pIdToCId.get(product)) {
         cIdToPId.get(customer).add(product);
      }
   }
   List<ProductPopularity> productPopularity = new java.util.ArrayList<>();
   for (Map.Entry<String, HashSet<Long>> entry : pIdToCId.entrySet()) {
      productPopularity
       .add(new ProductPopularity(entry.getKey(), entry.getValue()));
   }
   java.util.Collections.sort(productPopularity);
   List<CustomerDiversity> customerDiversity = new java.util.ArrayList<>();
   for (Map.Entry<Long, HashSet<String>> entry : cIdToPId.entrySet()) {
      customerDiversity
       .add(new CustomerDiversity(entry.getKey(), entry.getValue()));
   }
   java.util.Collections.sort(customerDiversity);
   TopProducts(productPopularity, 25);
   System.out.println();
   TopCustomers(customerDiversity, 25);
}

private static void TopProducts(List<ProductPopularity> product,
      int rank) {      
   System.out.println(rank + " most popular products:");
   for (int i = rank; i > 0; i--) {
      System.out.printf("%9s %d\n",
            product.get(25 - i).productId, 
            product.get(25 - i).popularity.size());
   }

}
private static void TopCustomers(List<CustomerDiversity> customer, 
      int rank) {      
   System.out.println(rank + " most varied buyers:");
   for (int i = rank; i > 0; i--) {
      System.out.printf("%9d %d\n",
            customer.get(25 - i).customerId, 
            customer.get(25 - i).diversity.size());
   }
}

   
   public static void main(String[] args) throws IOException {
      final int numFlavors = 5, numKinds = 7, numNames = 11;

      List<Product> products = new LinkedList<Product>();
      List<LineItem> lineItems = new LinkedList<LineItem>();
      List<Receipt> receipts = new LinkedList<Receipt>();
      List<Customer> customers = new LinkedList<Customer>();

      Scanner in = new Scanner(System.in);
      Random rnd;
      int numCsts, maxRcts, maxItems, c, prd, numPrds, numRcts, numItems;
      Customer cst;
      Receipt rct;
      
      int seed = in.nextInt();
      rnd = new Random(seed);
      idGap = new Random(seed);
      while (in.hasNextInt()) {
         numCsts = in.nextInt();
         maxRcts = in.nextInt();
         maxItems = in.nextInt();
         numPrds = in.nextInt();
         for (prd = 0; prd < numPrds; prd++)
            products.add(new Product("Prd" + prd, "Flavor" + prd % numFlavors,
                  "Kind" + prd % numKinds));
         
         for (c = 0; c < numCsts; c++) {
            cst = new Customer("First" + c % numNames, "Last" + c % numNames);
            customers.add(cst);
            numRcts = rnd.nextInt(maxRcts);
            while (numRcts-- > 0) {
               rct = new Receipt(cst.id);
               receipts.add(rct);
               numItems = rnd.nextInt(maxItems);
               while (numItems-- > 0) {
                  lineItems.add(new LineItem(rct.id,
                        "Prd" + rnd.nextInt(numPrds)));
               }
            }
         }
         analyzeSales(customers, receipts, lineItems, products);
      }
   }
}