package vehicle.type;

import java.util.Iterator;
import java.util.List;

import vehicle.validation.AbstractFieldValidator;

public class ListField<T extends Validateable<T>> extends AbstractField<List<T>> {
    private static final long serialVersionUID = 9023818915506036756L;

    private List<T> list;

    public ListField(final List<T> list) {
        setValue(list);
    }

    public ListField(final List<T> list, final AbstractFieldValidator validator) {
        super(validator);
        setValue(list);
    }

    @Override
    protected void setValueImpl(final List<T> value) {
        this.list = value;
    }

    @Override
    public List<T> getValue() {
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ListField<T> clone() {
        final ListField<T> result = (ListField<T>) super.clone();
        for (int i = 0; i < list.size(); ++i) {
            result.list.set(i, (T) list.get(i).clone());
        }
        return result;
    }

    public void addAll(final List<T> list) {
        list.addAll(list);
    }

    public void add(final T object) {
        list.add(object);
    }

    public Iterator<T> iterator() {
        return list.iterator();
    }

    public void remove(final T value) {
        list.remove(value);
    }
}
