#include <iostream>
using namespace std;

struct node {
	int coeff;
	int exp;
	node *next;
	node *prev;
	
	node(int c, int e) {
		coeff = c;
		exp = e;
		next = NULL;	
		prev = NULL;	
	}
};

void printPoly(node *poly) {
	node *t = poly;
	do {
		cout << t->coeff << "x^" << t->exp;
		t = t->next;
		if(t != poly) { 
			cout << " + ";
		} else {
			break;
		}
	} while (1);
	cout << endl;
}

node *insertAscending(node *poly, int coeff, int exp) {
	node *n = new node(coeff, exp);
	if(poly == NULL) {
		poly = 	n;
		poly->next = poly->prev = poly;
	} else {
		node *t = poly;
		while (t->exp < exp) {
			t = t->next;
			if(t == poly) break;		
		}
		if(t->exp == exp) {
			t->coeff += coeff;	
		} else {
			n->prev = t->prev;
			n->next = t;
			t->prev = n;
			n->prev->next = n;		
		}
		if(n->exp < poly->exp) poly = n;
	}
	return poly;
}

node *createPolynomial(int polynomial[][2], int N) {
	node *poly = NULL;
	for(int i = 0; i < N; i++) {
		poly = insertAscending(poly, polynomial[i][0], polynomial[i][1]);
	}
	return poly;
}

node *addPolynomials(node *poly1, node *poly2) {
	node *t1 = poly1, *t2 = poly2, *poly3 = NULL;
	do {
		poly3 = insertAscending(poly3, t1->coeff, t1->exp);		
		t1 = t1->next;	
	} while (t1 != poly1);
	do {
		poly3 = insertAscending(poly3, t2->coeff, t2->exp);		
		t2 = t2->next;	
	} while (t2 != poly2);
	return poly3;
}

node *multiplyPolynomials(node *poly1, node *poly2) {
	node *t1 = poly1, *poly3 = NULL;
	do {
		node *t2 = poly2;
		do {
			poly3 = insertAscending(poly3, t1->coeff * t2->coeff, t1->exp + t2->exp);
			t2 = t2->next;
		} while (t2 != poly2);
		t1 = t1->next;
	} while (t1 != poly1);
	return poly3;
}

int main() {
	int poly1[][2] = {{5, 0}, {4, 2}, {3,1}};
	int poly2[][2] = {{3, 2}, {6, 0}};	
	node *npoly1 = createPolynomial(poly1, 3);
	node *npoly2 = createPolynomial(poly2, 2);
	cout << "Poly 1: ";
	printPoly(npoly1);
	cout << "Poly 2: ";
	printPoly(npoly2);
	cout << "Poly 1 + Poly 2: ";
	printPoly(addPolynomials(npoly1, npoly2));
	cout << "Poly 1 * Poly 2: ";
	printPoly(multiplyPolynomials(npoly1, npoly2));
}
