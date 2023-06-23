package P4;
import java.io.Serializable;
import java.util.Iterator;
@SuppressWarnings("serial")
public class AnimalList implements Iterable<Animal>, Serializable{
    private int size;
    private AnimalNode<Animal> head, tail;
    public int size() {
        return size;
    }
    public boolean isEmpty() {
        return (size==0);
    }
    public void addFirst(Animal animal) {
        AnimalNode<Animal> newNode = new AnimalNode<Animal>(animal);
        newNode.next = head;
        head = newNode;
        if (isEmpty()) {
            tail = head;
        }
        size++;
    }

    public void addLast(Animal animal) {
        AnimalNode<Animal> n = new AnimalNode<Animal>(animal);
        if (isEmpty()) {
            head = n;
        } else {
            AnimalNode<Animal> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = n;
        }
        tail = n;
        size++;
    }
public void add(int index, Animal animal) {
    if (index < 0 || index > size) {
        throw new IndexOutOfBoundsException();
    } else if (index == 0) {
        addFirst(animal);
    } else if (index == size) {
        addLast(animal);
    } else {
        AnimalNode<Animal> node = new AnimalNode<Animal>(animal);
        AnimalNode<Animal> current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }
        node.next = current.next;
        current.next = node;
        size++;
    }
}
    public Animal removeFirst() {
        if(isEmpty())
            return null;
        else {
            AnimalNode<Animal> temp = head;
            head = head.next;
            if(head == null)
                tail = null;
            size--;
            return temp.element;
        }
    }



    public Animal removeLast() {
        if(isEmpty())
            return null;
        else if(size == 1)
            return removeFirst();
        else {
            AnimalNode<Animal> temp = tail;
            AnimalNode<Animal> current = head;
            for(int i = 0; i < size - 2; i++)
                current = current.next;
            tail = current;
            tail.next = null;
            size--;
            return temp.element;
        }
    }
    public Animal remove(int index) {
        if(index < 0 || index > size - 1)
            throw new IndexOutOfBoundsException();
        else if(index == size - 1)
            return removeLast();
        else if(index == 0)
            return removeFirst();
        else {
            AnimalNode<Animal> previous = head;
            for(int i = 0; i < index - 1; i++)
                previous = previous.next;
            AnimalNode<Animal> current = previous.next;
            previous.next = current.next;
            size--;
            return current.element;
        }
    }
    public Animal getFirst() {
        return isEmpty() ? null : head.element;
    }
    public Animal getLast() {
        return isEmpty() ? null : tail.element;
    }

    public Animal get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        int currentIndex = 0;
        AnimalNode<Animal> currentNode = head;

        while (currentIndex < index) {
            currentNode = currentNode.next;
            currentIndex++;
        }

        return currentNode.element;
    }
    public Animal set(int index, Animal animal) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        AnimalNode<Animal> node = new AnimalNode<Animal>(animal);
        AnimalNode<Animal> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        Animal temp = current.element;
        node.next = current.next;
        current = node;
        return temp;
    }

    public String toString() {
        String result = "";
        for(int i = 0; i < size(); i++){
            result += String.format("%-8s: %-5s at (%-2.1f,%-2.1f) Energy=%-7.1f\n",
                    get(i).getName(), get(i).isAlive()?"alive":"dead",get(i).getX(),get(i).getY(),get(i).getEnergy());
        }
        return result;
    }
    public Iterator<Animal> iterator() {
        return new MyIterator();
    }

    public AnimalList getHungryAnimals() {
        AnimalList hungryAnimals = new AnimalList();
        AnimalNode<Animal> current = head;
        while (current != null) {
            if (current.element.getEnergy() < 50) {
                hungryAnimals.addLast(current.element);
            }
            current = current.next;
        }
        return hungryAnimals.size() == 0 ? null : hungryAnimals;
    }

    public AnimalList getStarvingAnimals() {
        AnimalList starvingAnimals = new AnimalList();
        for(int i = 0; i < size(); i++) {
            if(get(i).getEnergy() < 17) {
                starvingAnimals.addLast(get(i));
            }
        }
        return starvingAnimals.isEmpty() ? null : starvingAnimals;

    }


    public AnimalList getAnimalsInBarn() {
        AnimalList barnAnimals = new AnimalList();
        for(int i = 0; i < size(); i++) {
            if((get(i).getX() >= 450 && get(i).getX() <= 550) && (get(i).getY() >= 50 && get(i).getY() <= 150)) {
                barnAnimals.addLast(get(i));
            }
        }
        return barnAnimals.isEmpty() ? null : barnAnimals;
    }
    public double getRequiredFood() {
        double sum = 0;
        AnimalNode<Animal> current = head;
        while (current != null) {
            sum += Math.max(0, 100 - current.element.getEnergy());
            current = current.next;
        }
        return sum;
    }
    @SuppressWarnings({ "hiding" })
    private static class AnimalNode<Animal> implements Serializable {
        Animal element;
        AnimalNode<Animal> next;
        public AnimalNode(Animal animal) {
            element = animal;
        }
    }
    public class MyIterator implements Iterator<Animal>{
        private AnimalNode<Animal> current = head;
        public boolean hasNext() {
            return (current != null);
        }
        public Animal next() {
            Animal temp = current.element;
            current = current.next;
            return temp;
        }
    }
    @SuppressWarnings("rawtypes")
    //bonus getbytype method
    public AnimalList getByType(Class c) {
        AnimalList temp = new AnimalList();
        AnimalNode<Animal> current = head;
        while(current != null) {
            if(c.isInstance(current.element)) {
                temp.addLast(current.element);
            }
            current = current.next;
        }
        return temp;
    }

}

