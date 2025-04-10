package estructuraDeDatos;

public class ListaEnlazada<T> {

    Nodo<T> inicio;
    
    public ListaEnlazada() {
        this.inicio=null;
    }
    
    public void insertar(T nodo){
        Nodo<T> newNodo=new Nodo<>(nodo);
        newNodo.siguiente=inicio;
        inicio=newNodo;
    }
    public void mostrar(){
        if(inicio==null)//osea no hay datos
            throw new IndexOutOfBoundsException("NO HAY DATOS A MOSTRAR");
        else{
            Nodo<T> actual=inicio;
            while(actual!=null){
                System.out.println(actual.valor.toString()+"\n");
                actual=(Nodo) actual.siguiente;
            }
        }
    }
}
