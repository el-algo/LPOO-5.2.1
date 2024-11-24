public class MinerExample {

    // Clase que representa la mina de minerales
    static class Mine {
        private int minerals = 10; // Cantidad total de minerales en la mina

        // Método sincronizado para extraer minerales
        public synchronized boolean extractMinerals(String minerName) {
            if (minerals > 0) {
                minerals--;
                System.out.println(minerName + " ha extraído un mineral. Quedan " + minerals + " minerales.");
                return true;
            } else {
                System.out.println("La mina está vacía. " + minerName + " no puede extraer más minerales.");
                return false;
            }
        }
    }

    // Clase que representa a un minero
    static class Miner extends Thread {
        private String name;
        private Mine mine;

        public Miner(String name, Mine mine) {
            this.name = name;
            this.mine = mine;
        }

        @Override
        public void run() {
            // Minero sigue extrayendo mientras haya minerales en la mina
            while (mine.extractMinerals(name)) {
                try {
                    // Simular tiempo de extracción con un pequeño descanso
                    Thread.sleep((int) (Math.random() * 100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Mine sharedMine = new Mine();

        // Crear varios mineros (hilos)
        Miner miner1 = new Miner("Minero 1", sharedMine);
        Miner miner2 = new Miner("Minero 2", sharedMine);
        Miner miner3 = new Miner("Minero 3", sharedMine);

        // Iniciar los hilos
        miner1.start();
        miner2.start();
        miner3.start();

        // Esperar a que los mineros terminen su trabajo
        try {
            miner1.join();
            miner2.join();
            miner3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Todos los mineros han terminado su trabajo. La mina está vacía.");
    }
}
