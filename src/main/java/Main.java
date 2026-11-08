import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    private static final String RUTA_DIR = "Archivos/";
    private static final String RUTA_RECETAS = "recetas.dat";
    private static final String RUTA_EXPORT="recetas.xml";
    private static final String RUTA_JSON="configuracion.json";
    private static int optionOld=-1;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("===== GESTOR DE RECETAS=====");
            System.out.println("0-Salir\n" +
                    "1-Crear un catálogo inicial con varias recetas.\n" +
                    "2-Mostrar todas las recetas almacenadas.\n" +
                    "3-Buscar una receta mediante su identificador.\n" +
                    "4-Modificar el tiempo de preparación de una receta.\n" +
                    "5-Añadir nuevas recetas al catálogo.\n" +
                    "6-Exportar recetas en XML\n");
            System.out.print("Seleccione una opcion: ");
            int option = sc.nextInt();


            if (option==0){
                int numRecetas=leerRecetas();
                Configuracion config=new Configuracion(numRecetas,optionOld);

                Gson gson = new GsonBuilder().setPrettyPrinting().create();

                try(FileWriter fw=new FileWriter(RUTA_DIR+RUTA_JSON)){
                    gson.toJson(config, fw);
                    System.out.println("Documento JSON creado correctamente.");
                }catch (IOException e){
                    System.out.println("ERROR: "+e.getMessage());
                }
                break;

            }
            if (option == 1) {
                optionOld=option;
                crearFicheroRecetas();
            }

            if (option == 2) {
                optionOld=option;
                leerRecetas();
            }
            if (option == 3) {
                optionOld=option;
                System.out.print("Introduzca el id de la receta que quiere buscar: ");
                int idBusca = sc.nextInt();
                leerRecetas(idBusca);
            }
            if (option == 4) {
                optionOld=option;
                System.out.print("Introduzca el id de la receta que quiere modificar el tiempo de preparacion: ");
                int idBusca = sc.nextInt();
                modificarTiempo(idBusca);
            }
            if (option== 5){
                optionOld=option;
                añadirRecetas();
            }
            if (option==6){
                optionOld=option;
                exportarEnXML();
            }
        }

    }

    public static Receta[] recetasDefault() {
        ArrayList<String> ingredientes1 = new ArrayList<>();
        ingredientes1.add("Patatas");
        ingredientes1.add("Huevos");
        ingredientes1.add("Cebolla");
        ingredientes1.add("Aceite de oliva");
        ingredientes1.add("Sal");

        ArrayList<String> ingredientes2 = new ArrayList<>();
        ingredientes2.add("Espaguetis");
        ingredientes2.add("Guanciale o panceta");
        ingredientes2.add("Yemas de huevo");
        ingredientes2.add("Queso Pecorino Romano");
        ingredientes2.add("Pimienta negra");

        ArrayList<String> ingredientes3 = new ArrayList<>();
        ingredientes3.add("Lechuga romana");
        ingredientes3.add("Pechuga de pollo");
        ingredientes3.add("Pan de molde");
        ingredientes3.add("Queso parmesano");
        ingredientes3.add("Salsa César");

        ArrayList<String> ingredientes4 = new ArrayList<>();
        ingredientes4.add("Pechugas de pollo");
        ingredientes4.add("Zumo de limón");
        ingredientes4.add("Caldo de pollo");
        ingredientes4.add("Maicena");
        ingredientes4.add("Ajo");
        ingredientes4.add("Aceite de oliva");
        ingredientes4.add("Sal");

        ArrayList<String> ingredientes5 = new ArrayList<>();
        ingredientes5.add("Queso crema");
        ingredientes5.add("Nata para montar");
        ingredientes5.add("Huevos");
        ingredientes5.add("Azúcar");
        ingredientes5.add("Galletas tipo María");
        ingredientes5.add("Mantequilla");


        String preparacion1 = "Pelar y cortar las patatas y la cebolla en trozos pequeños, luego freírlas\n " +
                "en una sartén con abundante aceite a fuego lento hasta que estén tiernas. Batir los huevos\n " +
                "en un bol amplio, añadir las patatas y cebolla escurridas junto con la sal, y mezclar bien.\n " +
                "Cuajar la mezcla en una sartén con un toque de aceite por ambos lados hasta el punto deseado.";
        String preparacion2 = "Cocer la pasta en agua hirviendo con sal hasta que esté al dente. Dorar el guanciale\n " +
                "en una sartén sin aceite hasta que esté crujiente. En un bol, batir las yemas de huevo junto con\n" +
                " el queso rallado y abundante pimienta negra molida. Mezclar la pasta caliente con el guanciale y,\n " +
                "fuera del fuego, incorporar la crema de huevos y queso removiendo rápidamente para evitar que cuaje.";
        String preparacion3 = "Cocinar la pechuga de pollo a la plancha y cortarla en tiras finas. Cortar el pan en cubos\n " +
                "pequeños y tostarlos en una sartén con un chorrito de aceite hasta que estén dorados y crujientes\n " +
                "(picatostes). Lavar y trocear la lechuga romana, colocarla en una ensaladera y añadir el pollo,\n " +
                "los picatostes, virutas de queso parmesano y la salsa César al gusto.";
        String preparacion4 = "Cortar las pechugas en filetes o dados, salpimentarlas y dorarlas en una sartén\n " +
                "con aceite de oliva. En un recipiente, mezclar el zumo de limón, el caldo de pollo, un diente\n " +
                "de ajo picado y una cucharadita de maicena disuelta en agua fría. Verter esta mezcla en la sartén\n " +
                "junto con el pollo y cocinar a fuego medio hasta que la salsa reduzca y espese.";
        String preparacion5 = "Triturar las galletas y mezclarlas con la mantequilla fundida para compactarlas\n " +
                "en la base de un molde desmontable. En un bol, batir el queso crema junto con el azúcar,\n " +
                "los huevos y la nata líquida hasta obtener una mezcla fina y sin grumos. Verter sobre la\n " +
                "base de galletas y hornear a 180°C durante unos 40-45 minutos hasta que esté firme. Dejar enfriar " +
                "antes de servir";

        Receta tortillaDePatata = new Receta("Tortilla de patata", ingredientes1, preparacion1, 0.35);
        Receta carbonara = new Receta("Carbonara", ingredientes2, preparacion2, 0.2);
        Receta ensalaCesar = new Receta("Ensalada César", ingredientes3, preparacion3, 0.15);
        Receta polloAlLimon = new Receta("Pollo al limón", ingredientes4, preparacion4, 0.25);
        Receta tartaQueso = new Receta("Tarta de queso", ingredientes5, preparacion5, 0.55);

        return new Receta[]{tortillaDePatata, carbonara, ensalaCesar, polloAlLimon, tartaQueso};
    }

    public static void crearFicheroRecetas() {
        try (ObjectOutputStream dOut = new ObjectOutputStream(new FileOutputStream(RUTA_DIR + RUTA_RECETAS))) {

            File catalogoInicial = new File(RUTA_DIR + RUTA_RECETAS);

            if (catalogoInicial.createNewFile()) {
                System.out.println("Archivo creado correctamente");
            } else {
                System.out.println("El archivo ya existe");
            }

            Receta[] recetas = recetasDefault();

            for (int i = 0; i < recetas.length; i++) {
                dOut.writeObject(recetas[i]);
            }
            System.out.println("Archivo generado correctamente");

        } catch (IOException e) {
            System.out.println("Error al escribir el archivo");
        }
    }

    public static int leerRecetas() {

        ArrayList<Receta>recetas=new ArrayList<>();

        try (ObjectInputStream dIn = new ObjectInputStream(new FileInputStream(RUTA_DIR + RUTA_RECETAS))) {

            while (true) {
                Receta receta = (Receta) dIn.readObject();
                recetas.add(receta);

                int idReceta = receta.getNumReceta();
                String nombre = receta.getNombre();
                ArrayList<String> ingredientes = receta.getIngredientes();
                String preparacion = receta.getPreparacion();
                double tiempo = receta.getTiempoPreparacion();

                System.out.println("----" + idReceta + "-" + nombre + "----");
                System.out.println("Ingredientes: ");
                for (int i = 0; i < ingredientes.size(); i++) {
                    System.out.println("-" + ingredientes.get(i));
                }
                System.out.println("Preparacion: ");
                System.out.println(preparacion);
                System.out.println("Tiempo de preparacion: " + tiempo);

            }

        } catch (EOFException e) {
            System.out.println("--- Fin del archivo ---");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo Recetas");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return recetas.size();
    }

    public static void leerRecetas(int id) {
        try (ObjectInputStream dIn = new ObjectInputStream(new FileInputStream(RUTA_DIR + RUTA_RECETAS))) {
            while (true) {
                Receta receta = (Receta) dIn.readObject();

                int idReceta = receta.getNumReceta();
                String nombre = receta.getNombre();
                ArrayList<String> ingredientes = receta.getIngredientes();
                String preparacion = receta.getPreparacion();
                double tiempo = receta.getTiempoPreparacion();
                if (id == idReceta) {
                    System.out.println("----" + idReceta + "-" + nombre + "----");
                    System.out.println("Ingredientes: ");
                    for (int i = 0; i < ingredientes.size(); i++) {
                        System.out.println("-" + ingredientes.get(i));
                    }
                    System.out.println("Preparacion: ");
                    System.out.println(preparacion);
                    System.out.println("Tiempo de preparacion: " + tiempo);
                }

            }

        } catch (EOFException e) {
            System.out.println("--- Fin del archivo ---");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo Recetas");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void modificarTiempo(int id) {

        Scanner sc = new Scanner(System.in);
        ArrayList<Receta> recetas = new ArrayList<>();

        try (ObjectInputStream dIn = new ObjectInputStream(new FileInputStream(RUTA_DIR + RUTA_RECETAS))) {

            while (true) {
                Receta receta = (Receta) dIn.readObject();
                recetas.add(receta);
            }

        } catch (EOFException e) {
            System.out.println("--- Fin del archivo ---");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo Recetas");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < recetas.size(); i++) {
            int idReceta = recetas.get(i).getNumReceta();
            if (id == idReceta) {
                System.out.print("Introduce el nuevo tiempo de preparacion: ");
                double nuevoTiempo = sc.nextDouble();
                recetas.get(i).setTiempoPreparacion(nuevoTiempo);
                System.out.println("Tiempo modificado correctamente");
            }
        }
        try (ObjectOutputStream dIn = new ObjectOutputStream(new FileOutputStream(RUTA_DIR + RUTA_RECETAS))) {
            for (int i = 0; i < recetas.size(); i++) {
                dIn.writeObject(recetas.get(i));
            }

        } catch (EOFException e) {
            System.out.println("--- Fin del archivo ---");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo Recetas");
        }
    }

    public static void añadirRecetas(){
        Scanner sc = new Scanner(System.in);
        ArrayList<Receta> recetas = new ArrayList<>();

        try (ObjectInputStream dIn = new ObjectInputStream(new FileInputStream(RUTA_DIR + RUTA_RECETAS))) {
            while (true) {
                Receta receta = (Receta) dIn.readObject();
                recetas.add(receta);
            }

        } catch (EOFException e) {
            System.out.println("--- Fin del archivo ---");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo Recetas");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        ArrayList<String> ingredientes=new ArrayList<>();
        System.out.print("Introduce el nombre de la nueva receta: ");
        String nombre=sc.next();
        System.out.println("De cuantos ingredientes cuenta la receta: ");
        int numIngredientes=sc.nextInt();
        for (int i=0;i<numIngredientes;i++){
            System.out.println("Introduce el ingrediente numero "+(i+1)+":");
            String ingrediente=sc.next();
            ingredientes.add(ingrediente);
        }
        System.out.println("Introduce la preparacion: ");
        sc.nextLine();
        String preparacion=sc.nextLine();
        System.out.println();
        System.out.println("Introduce el tiempo de preparacion");
        double tiempo=sc.nextDouble();

        Receta receta=new Receta(nombre,ingredientes,preparacion,tiempo);
        recetas.add(receta);

        try (ObjectOutputStream dIn = new ObjectOutputStream(new FileOutputStream(RUTA_DIR + RUTA_RECETAS))) {
            for (int i = 0; i < recetas.size(); i++) {
                dIn.writeObject(recetas.get(i));
            }
        } catch (EOFException e) {
            System.out.println("--- Fin del archivo ---");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo Recetas");
        }

    }

    public static void exportarEnXML() {
        ArrayList<Receta> recetas = new ArrayList<>();

        try (ObjectInputStream dIn = new ObjectInputStream(new FileInputStream(RUTA_DIR + RUTA_RECETAS))) {
            while (true) {
                Receta receta = (Receta) dIn.readObject();
                recetas.add(receta);
            }

        } catch (EOFException e) {
            System.out.println("--- Fin del archivo ---");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo Recetas");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try{
            DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
            DocumentBuilder builder= factory.newDocumentBuilder();
            Document documento= builder.newDocument();



            Element recetasRaiz =documento.createElement("Recetas");
            documento.appendChild(recetasRaiz);

            for (int i=0;i< recetas.size();i++){
                int id=recetas.get(i).getNumReceta();
                String nombre=recetas.get(i).getNombre();
                ArrayList<String> ingredientes=recetas.get(i).getIngredientes();
                String preparacion=recetas.get(i).getPreparacion();
                double tiempo=recetas.get(i).getTiempoPreparacion();

                Element receta= documento.createElement("Receta");
                receta.setAttribute("id",id+"");
                recetasRaiz.appendChild(receta);

                Element nom=documento.createElement("Nombre");
                nom.setTextContent(nombre);
                receta.appendChild(nom);

                for (int j=0;j<ingredientes.size();j++){
                    Element ingrediente=documento.createElement("Ingrediente");
                    ingrediente.setTextContent(ingredientes.get(j));
                    receta.appendChild(ingrediente);
                }

                Element prepa=documento.createElement("Preparacion");
                prepa.setTextContent(preparacion);
                receta.appendChild(prepa);

                Element tiempoPrepa=documento.createElement("Tiempo_preparacion");
                tiempoPrepa.setTextContent(tiempo+"");
                receta.appendChild(tiempoPrepa);
            }

            Transformer transformer=TransformerFactory.newInstance().newTransformer();

            transformer.transform(new DOMSource(documento),new StreamResult(new File(RUTA_DIR+RUTA_EXPORT)));
            System.out.println("Documento XML generado correctamente");
        }catch (ParserConfigurationException e){
            System.out.println("ERROR: "+e.getMessage());
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }

    public static void leerJSON(){
        Gson gson = new Gson();
        try (FileReader fr = new FileReader(RUTA_DIR+RUTA_EXPORT)) {
            // Convierte el documento JSON en un array de objetos
            Configuracion[] config = gson.fromJson(fr, Configuracion[].class);

            // Muestra las películas
            for (Configuracion con : config) {
                System.out.println(con);
                System.out.println();
            }
        } catch (IOException e) {
            System.out.println("Error al leer el documento JSON.");
        }

    }
}
