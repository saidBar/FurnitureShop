package com.example.furnitureshop.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.furnitureshop.com.example.furnitureshop.util.Constants.USER_COLLECTION
import com.example.furnitureshop.com.example.furnitureshop.util.RegisterFieldsState
import com.example.furnitureshop.com.example.furnitureshop.util.RegisterValidation
import com.example.furnitureshop.com.example.furnitureshop.util.validateEmail
import com.example.furnitureshop.com.example.furnitureshop.util.validatePassword
import com.example.furnitureshop.data.User
import com.example.furnitureshop.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val db: FirebaseFirestore
) : ViewModel() {
    private val _register = MutableStateFlow<Resource<User>>(Resource.Unspecified())
    val register: Flow<Resource<User>> = _register

    private val _validation = Channel<RegisterFieldsState>()
    public val validation = _validation.receiveAsFlow()
    fun createAccountWithEmailAndPassword(user: User, password: String) {
        if (checkValidation(user, password)) {
            viewModelScope.launch {
                _register.emit(Resource.Loading())
                firebaseAuth.createUserWithEmailAndPassword(user.Email, password)
                    .addOnSuccessListener {
                        it.user?.let {
                            viewModelScope.launch {
                                saveUserInfo(it.uid, user)
                            }
                        }
                    }.addOnFailureListener {
                        viewModelScope.launch {
                            _register.value = Resource.Error(it.message.toString())
                        }
                    }
            }
        } else {
            val registerFieldsState = RegisterFieldsState(
                validateEmail(user.Email), validatePassword(password)
            )
            viewModelScope.launch {
                _validation.send(registerFieldsState)
            }
        }
    }

    private fun saveUserInfo(userUid: String, user: User) {
        db.collection(USER_COLLECTION)
            .document(userUid)
            .set(user)
            .addOnSuccessListener {
                _register.value = Resource.Success(user)
            }.addOnFailureListener {
                _register.value = Resource.Error(it.message.toString())
            }
    }

    private fun checkValidation(user: User, password: String): Boolean {
        val emailValidation = validateEmail(user.Email)
        val passwordValidation = validatePassword(password)
        val shouldRegister = emailValidation is RegisterValidation.Success &&
                passwordValidation is RegisterValidation.Success

        return shouldRegister
    }
}
