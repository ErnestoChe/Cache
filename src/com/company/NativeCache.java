package com.company;

import java.lang.reflect.Array;

class NativeCache<T>
{
    public int size;
    public String [] slots;
    public T [] values;
    public int [] hits;
    public int step;

    public NativeCache(int sz, Class clazz){
        size = sz;
        slots = new String[size];
        step = 3;
        values = (T[]) Array.newInstance(clazz, this.size);
        hits = new int[size];
        for (int i = 0; i < size; i++) {
            hits[i] = 0;
        }
    }

    public int hashFun(String key) {
        // всегда возвращает корректный индекс слота
        char[] n = key.toCharArray();
        int v = 0;
        for(int i = 0; i<n.length;i++){
            v += (int)n[i];
        }
        int hash = v % size;
        return hash;
    }

    public int seekSlot(String value)
    {
        // находит индекс пустого слота для значения, или -1
        int hash = hashFun(value);
        int count = 0;
        while(slots[hash] != null){
            hash += step;
            if(hash >= slots.length){
                int dif = hash - slots.length;
                hash = dif;
            }
            count++;
            if(count > size) return -1;
        }
        return hash;
    }

    public boolean isKey(String key) {
        // возвращает true если ключ имеется,
        // иначе false
        int a = hashFun(key);
        boolean flag = false;
        int count = 0;
        while(slots[a] != null){
            if(slots[a].equals(key)){
                //flag = true;
                return true;
            }
            a += step;
            if(a >= slots.length){
                int dif = a - slots.length;
                a = dif;
            }
            if(slots[a] == null){
                return false;
            }
            count++;
            if(count>size){
                flag = false;
                return flag;
            }
        }
        return flag;
    }

    public void put(String key, T value) {
        // гарантированно записываем
        // значение value по ключу key
        if(isKey(key)){
            int ind = hashFun(key);
            while(!slots[ind].equals(key)){
                ind +=step;
                if(ind >= slots.length){
                    int dif = ind - slots.length;
                    ind = dif;
                }
            }
            //values[ind] = value;
            hits[ind]++;
        }else{
            int index = seekSlot(key);
            if(index == -1){
                int tmp_ind = hashFun(key);
                int min_hits = hits[tmp_ind];
                int count = 0;
                while(count<size){
                    min_hits = Math.min(min_hits, hits[tmp_ind]);
                    tmp_ind += step;
                    if(tmp_ind >= slots.length){
                        int dif = tmp_ind - slots.length;
                        tmp_ind = dif;
                    }
                    count++;
                }
                index = tmp_ind;
            }
            values[index] = value;
            slots[index] = key;
            hits[index] = 0;
        }
    }

    public T get(String key) {
        // возвращает value для key,
        // или null если ключ не найден
        if(!isKey(key))
            return null;
        int index = hashFun(key);
        int h = index;
        while(slots[index] != null){
            if(slots[index].equals(key)){
                break;
            }else{
                index+=step;
                if(index >= slots.length){
                    int dif = index - slots.length;
                    index = dif;
                }
            }
        }
        if(index == -1){
            return null;
        }else{
            hits[index]++;
            return values[index];
        }
    }

    public void log(){
        for (int i = 0; i < size; i++) {
            System.out.println(slots[i] + " " + values[i] + " " + hits[i]);
        }
        System.out.println();
    }
}
