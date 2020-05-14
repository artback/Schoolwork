public class ShellSort {
    public void Shellsort(Object[] data) {
        int i, j, k, h, hCnt, increments[] = new int[20];
        // create an appropriate number of increments h
        for (h = 1, i = 0; h < data.length; i++) {
            increments[i] = h;
            h = 3 * h + 1;
        }
        for (i--; i >= 0; i--) {
            h = increments[i];
            for (hCnt = h; hCnt < 2 * h; hCnt++) {
                for (j = hCnt; j < data.length; ) {
                    Comparable tmp = (Comparable) data[j];
                    k = j;
                    while (k - h >= 0 && tmp.compareTo(data[k - h]) < 0) {
                        data[k] = data[k - h];
                        k -= h;
                    }
                    data[k] = tmp;
                    j += h;
                }
            }
        }
    }

}
