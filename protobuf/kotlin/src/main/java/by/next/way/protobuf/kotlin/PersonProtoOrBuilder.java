// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: PersonProto.proto

package by.next.way.protobuf.kotlin;

public interface PersonProtoOrBuilder extends
    // @@protoc_insertion_point(interface_extends:PersonProto)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string firstName = 1;</code>
   */
  java.lang.String getFirstName();
  /**
   * <code>string firstName = 1;</code>
   */
  com.google.protobuf.ByteString
      getFirstNameBytes();

  /**
   * <code>string lastName = 2;</code>
   */
  java.lang.String getLastName();
  /**
   * <code>string lastName = 2;</code>
   */
  com.google.protobuf.ByteString
      getLastNameBytes();

  /**
   * <code>string emailAddress = 3;</code>
   */
  java.lang.String getEmailAddress();
  /**
   * <code>string emailAddress = 3;</code>
   */
  com.google.protobuf.ByteString
      getEmailAddressBytes();

  /**
   * <code>string homeAddress = 4;</code>
   */
  java.lang.String getHomeAddress();
  /**
   * <code>string homeAddress = 4;</code>
   */
  com.google.protobuf.ByteString
      getHomeAddressBytes();

  /**
   * <code>repeated .PersonProto.PhoneNumber phoneNumbers = 5;</code>
   */
  java.util.List<by.next.way.protobuf.kotlin.PersonProto.PhoneNumber> 
      getPhoneNumbersList();
  /**
   * <code>repeated .PersonProto.PhoneNumber phoneNumbers = 5;</code>
   */
  by.next.way.protobuf.kotlin.PersonProto.PhoneNumber getPhoneNumbers(int index);
  /**
   * <code>repeated .PersonProto.PhoneNumber phoneNumbers = 5;</code>
   */
  int getPhoneNumbersCount();
  /**
   * <code>repeated .PersonProto.PhoneNumber phoneNumbers = 5;</code>
   */
  java.util.List<? extends by.next.way.protobuf.kotlin.PersonProto.PhoneNumberOrBuilder> 
      getPhoneNumbersOrBuilderList();
  /**
   * <code>repeated .PersonProto.PhoneNumber phoneNumbers = 5;</code>
   */
  by.next.way.protobuf.kotlin.PersonProto.PhoneNumberOrBuilder getPhoneNumbersOrBuilder(
      int index);
}
