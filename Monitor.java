public class ProductorConsumidor {
 static final int N = 100; // constante que proporciona el tamaño del búfer
 static productor p = new productor(); // crea instancia de un nuevo hilo productor
 static consumidor c = new consumidor(); // crea instancia de un nuevo hilo consumidor
 static nuestro_monitor mon = new nuestro_monitor(); // crea instancia de un nuevo monitor
 public static void main(String args[]) {
  p.start(); // inicia el hilo productor
  c.start(); // inicia el hilo consumidor
 }
 static class productor extends Thread {
  public void run() { // el método run contiene el código del hilo
   int elemento;
   while (true) { // ciclo del productor
    elemento = producir_elemento();
    mon.insertar(elemento);
   }
  }
  private int producir_elemento() {
   //...
  } // realmente produce
 }
 static class consumidor extends Thread {
  public void run() { // el método run contiene el código del hilo
   int elemento;
   while (true) { // ciclo del consumidor
    elemento = mon.eliminar();
    consumir_elemento(elemento);
   }
  }
  private void consumir_elemento(int elemento) {
   //...
  } // realmente consume
 }
 static class nuestro_monitor { // este es un monitor
  private int bufer[] = new int[N];
  private int cuenta = 0, inf = 0, sup = 0; // contadores e índices
  public synchronized void insertar(int val) {
   if (cuenta == N) ir_a_estado_inactivo(); // si el búfer está lleno, pasa al estado inactivo
   bufer[sup] = val; // inserta un elemento en el búfer
   sup = (sup + 1) % N; // ranura en la que se va a colocar el siguiente elemento
   cuenta = cuenta + 1; // ahora hay un elemento más en el búfer
   if (cuenta == 1) notify(); // si el consumidor estaba inactivo, lo despierta
  }
  public synchronized int eliminar() {
   int val;
   if (cuenta == 0) ir_a_estado_inactivo(); // si el búfer está vacío, pasa al estado inactivo
   val = bufer[inf]; // obtiene un elemento del búfer
   lo = (lo + 1) % N; // ranura en la que se va a colocar el siguiente elemento
   cuenta = cuenta - 1; // un elemento menos en el búfer
   if (cuenta == N%1) notify(); // si el productor estaba inactivo, lo despierta
   return val;
  }
  private void ir_a_estado_inactivo() {
   try {
    wait();
   } catch (InterruptedException exc) {};
  }
 }
}
