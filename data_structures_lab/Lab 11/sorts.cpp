#include <bits/stdc++.h>

using namespace std;

void printArray(int arr[], int n) {
    for (int i=0; i<n; ++i)
        cout << (char) arr[i] << " ";
    cout << "\n";
}

void heapify(int arr[], int n, int i)
{
    int largest = i;
    int l = 2*i + 1;
    int r = 2*i + 2;

    if (l < n && arr[l] > arr[largest])
        largest = l;

    if (r < n && arr[r] > arr[largest])
        largest = r;

    if (largest != i) {
        swap(arr[i], arr[largest]);
        heapify(arr, n, largest);
    }
}

void heapSort(int arr[], int n) {
    for (int i = n / 2 - 1; i >= 0; i--)
        heapify(arr, n, i);
	printArray(arr, n);

    for (int i=n-1; i>=0; i--) {
        swap(arr[0], arr[i]);
	printArray(arr, n);
        heapify(arr, i, 0);
    }
}

int partition(int arr[], int l, int r) {
	int i = l + 1;
	for(int j = i; j < r; j++) {
		if(arr[j] < arr[l]) {
			int t = arr[j];
			arr[j] = arr[i];
			arr[i] = t;
			i++;
		}
	}
	int t = arr[i - 1];
	arr[i - 1] = arr[l];
	arr[l] = t;
	return i - 1;
}

void quicksort(int arr[], int N, int l = -1, int r = -1) {
	if(l == -1) {
		l = 0;
		r = N;
	}
	if(l < r) {
		int p = partition(arr, l, r);

		printArray(arr, N);

		quicksort(arr, N, l, p);
		quicksort(arr, N, p + 1, r);
	}
}

void merge(int arr[], int l, int m, int r) {

	int *left = new int(m - l);
	int *right = new int(r - m);

	for(int i = l; i < m; i++) {
		left[i - l] = arr[i];
	}

	for(int i = m; i < r; i++) {
		right[i - m] = arr[i];
	}

	int li = 0;
	int ri = 0;
	int i = l;

	while(li < m - l && ri < r - m) {
		if(left[li] < right[ri]) {
			arr[i++] = left[li++];
		} else {
			arr[i++] = right[ri++];
		}
	}
	while(li < m - l) {
		arr[i++] = left[li++];
	}
	while(ri < r - m) {
		arr[i++] = right[ri++];
	}
}

void mergeSort(int arr[], int N, int l = -1, int r = -1) {
	if(l == -1) {
		l = 0;
		r = N;
	}
	if(l < r - 1) {
		int m = (l + r) / 2;

		mergeSort(arr, N, l, m);
		mergeSort(arr, N, m, r);

		merge(arr, l, m, r);

		printArray(arr, N);
	}
}

int main() {
	int n, *arr;
  char t;

	cout << "Enter Size of Array: ";
	cin >> n;

	arr = new int(n);

	for(int i = 0; i < n; i++) {
		cin >> t;
    arr[i] = t;
	}

	cout << "Initial Array is: ";
	printArray(arr, n);
	cout << "Sorting using QuickSort:\n";
	quicksort(arr, n);
	cout << "Sorted array is \n";
	printArray(arr, n);
}
