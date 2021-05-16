#include <bits/stdc++.h> 
#include<iostream>
#define COUNT 10
using namespace std; 
 

class node 
{ 
	public: 
	int data; 
	node *left; 
	node *right; 
}; 
 
node* newNode (int data) 
{ 
	node* temp = new node(); 
	temp->data = data; 
	temp->left = temp->right = NULL; 
	return temp; 
} 

void print2DUtil(node *root, int space)  
{    
    if (root == NULL)  
        return;  
    space += COUNT;  
    print2DUtil(root->right, space);  
    cout<<endl;  
    for (int i = COUNT; i < space; i++)  
        cout<<" ";  
    cout<<"( "<<root->data<<" )\n";  
    print2DUtil(root->left, space);  
}  

node* constructTreeUtil (int pre[], int post[], int* preIndex, int l, int h, int size) 
{  
	if (*preIndex >= size || l > h || pre[*preIndex]==0) 
		return NULL; 

	node* root = newNode ( pre[*preIndex] ); 
	++*preIndex; 

	if (l == h) 
		return root; 
 
	int i; 
	for (i = l; i <= h; ++i) 
		if (pre[*preIndex] == post[i]) 
			break; 

	if (i <= h) 
	{ 
		root->left = constructTreeUtil (pre, post, preIndex, l, i, size); 
		root->right = constructTreeUtil (pre, post, preIndex, i + 1, h, size); 
	} 
	return root; 
}
node *constructTree (int pre[], int post[], int size) 
{ 
	int preIndex = 0; 
	return constructTreeUtil (pre, post, &preIndex, 0, size - 1, size); 
}

int main () 
{ 	int n;
	int pre[50]; 
	int post[50];
	printf("Enter the number of nodes: ");	
	scanf("%d",&n);
	printf("Enter the preorder traversal: ");
	for(int i=0;i<n;i++)
		scanf("%d",&pre[i]);
	printf("Enter the postorder traversal: ");
	for(int i=0;i<n;i++)
		scanf("%d",&post[i]);
	int size = sizeof( pre ) / sizeof( pre[0] ); 

	node *root = constructTree(pre, post, size); 

	cout<<"The constructed tree: \n"; 
	print2DUtil(root,0); 

	return 0; 
} 
