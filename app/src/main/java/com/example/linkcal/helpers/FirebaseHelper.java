package com.example.linkcal.helpers;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Map;

public class FirebaseHelper {
    private static FirebaseHelper instance;
    private final FirebaseFirestore firestore;


    public interface OnSuccessCallback<T> {
        void onSuccess(T result);
    }

    public interface OnFailureCallback {
        void onFailure(Exception e);
    }

    private FirebaseHelper() {
        firestore = FirebaseFirestore.getInstance();
    }

    public static synchronized FirebaseHelper getInstance() {
        if (instance == null) {
            instance = new FirebaseHelper();
        }
        return instance;
    }


    public void addDocument(String collection, Map<String, Object> data,
                            OnSuccessCallback<String> successCallback,
                            OnFailureCallback failureCallback) {
        firestore.collection(collection)
                .add(data)
                .addOnSuccessListener(documentReference -> {
                    if (successCallback != null) {
                        successCallback.onSuccess(documentReference.getId());
                    }
                })
                .addOnFailureListener(e -> {
                    if (failureCallback != null) {
                        failureCallback.onFailure(e);
                    }
                });
    }


    public void updateDocument(String collection, String documentId,
                               Map<String, Object> data,
                               OnSuccessCallback<Void> successCallback,
                               OnFailureCallback failureCallback) {
        firestore.collection(collection)
                .document(documentId)
                .update(data)
                .addOnSuccessListener(aVoid -> {
                    if (successCallback != null) {
                        successCallback.onSuccess(null);
                    }
                })
                .addOnFailureListener(e -> {
                    if (failureCallback != null) {
                        failureCallback.onFailure(e);
                    }
                });
    }


    public void deleteDocument(String collection, String documentId,
                               OnSuccessCallback<Void> successCallback,
                               OnFailureCallback failureCallback) {
        firestore.collection(collection)
                .document(documentId)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    if (successCallback != null) {
                        successCallback.onSuccess(null);
                    }
                })
                .addOnFailureListener(e -> {
                    if (failureCallback != null) {
                        failureCallback.onFailure(e);
                    }
                });
    }


    public void getDocument(String collection, String documentId,
                            OnSuccessCallback<DocumentSnapshot> successCallback,
                            OnFailureCallback failureCallback) {
        firestore.collection(collection)
                .document(documentId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (successCallback != null) {
                        successCallback.onSuccess(documentSnapshot);
                    }
                })
                .addOnFailureListener(e -> {
                    if (failureCallback != null) {
                        failureCallback.onFailure(e);
                    }
                });
    }


    public void getAllDocuments(String collection,
                                OnSuccessCallback<QuerySnapshot> successCallback,
                                OnFailureCallback failureCallback) {
        firestore.collection(collection)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (successCallback != null) {
                        successCallback.onSuccess(queryDocumentSnapshots);
                    }
                })
                .addOnFailureListener(e -> {
                    if (failureCallback != null) {
                        failureCallback.onFailure(e);
                    }
                });
    }


    public void queryDocuments(String collection, String field, Object value,
                               OnSuccessCallback<QuerySnapshot> successCallback,
                               OnFailureCallback failureCallback) {
        firestore.collection(collection)
                .whereEqualTo(field, value)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (successCallback != null) {
                        successCallback.onSuccess(queryDocumentSnapshots);
                    }
                })
                .addOnFailureListener(e -> {
                    if (failureCallback != null) {
                        failureCallback.onFailure(e);
                    }
                });
    }


    public void setDocument(String collection, String documentId,
                            Map<String, Object> data,
                            OnSuccessCallback<Void> successCallback,
                            OnFailureCallback failureCallback) {
        firestore.collection(collection)
                .document(documentId)
                .set(data)
                .addOnSuccessListener(aVoid -> {
                    if (successCallback != null) {
                        successCallback.onSuccess(null);
                    }
                })
                .addOnFailureListener(e -> {
                    if (failureCallback != null) {
                        failureCallback.onFailure(e);
                    }
                });
    }
}